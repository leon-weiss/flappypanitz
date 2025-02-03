package name.panitz.game2d.flappypanitz;

import name.panitz.game2d.Game;
import name.panitz.game2d.GameObj;
import name.panitz.game2d.SwingScreen;
import name.panitz.game2d.flappypanitz.Screen.FontLoader;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("all")
public class FlappyGame implements Game {
    private final int GAME_WIDTH = 1200;
    private final int GAME_HEIGHT = 800;

    private final double GRAVITY = 0.5;
    private final double FLAP_STRENGTH = -12;

    private final double PIPE_WIDTH = 60;
    private final double PIPE_GAP = 250;
    private final double PIPE_SPEED = 4;
    private final int SPAWN_THRESHOLD = 50; // Abstand zwischen Röhren...

    private Player player;
    private List<Pipe> pipes;

    private boolean lost = false;
    private int score = 0;
    private int spawnCounter = 0;

    private final Character selectedCharacter;

    private boolean gameOverSoundPlayed = false;

    private List<HighScore> highScores = null;
    private String highScoreErrorMessage;

    private boolean scoreSubmitted = false;

    private final FontLoader fontLoader = new FontLoader();
    Font pixel24PxFont = fontLoader.loadFont("pixel", "Poxast", "Arial", 24);
    Font pixel48PxFont = fontLoader.loadFont("pixel", "Poxast", "Arial", 48);

    public FlappyGame(Character selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
        init();
    }

    @Override
    public void init() {

        player = new Player(100, GAME_HEIGHT / 2, 40, 40, GRAVITY, selectedCharacter);
        pipes = new ArrayList<>();
        lost = false;
        score = 0;
        spawnCounter = 0;
        highScores = null;
        gameOverSoundPlayed = false;
        scoreSubmitted = false;

        SoundPlayer.playBackgroundMusic("/assets/audio/background.wav");
    }

    @Override
    public int width() {
        return GAME_WIDTH;
    }

    @Override
    public int height() {
        return GAME_HEIGHT;
    }

    @Override
    public GameObj player() {
        return player;
    }

    @Override
    public List<List<? extends GameObj>> goss() {
        return List.of(pipes);
    }

    @Override
    public void doChecks() {
        Rectangle birdRect = new Rectangle((int) player.pos().x, (int) player.pos().y,
                (int) player.width(), (int) player.height());

        Iterator<Pipe> it = pipes.iterator();
        while (it.hasNext()) {
            Pipe pipe = it.next();

            if (pipe.getTopRect().intersects(birdRect) || pipe.getBottomRect().intersects(birdRect)) {
                lost = true;
            }

            if (!pipe.passed && pipe.pos().x + pipe.width() < player.pos().x) {
                score++;
                pipe.passed = true;
            }

            if (pipe.pos().x + pipe.width() < 0) {
                it.remove();
            }
        }

        if (player.pos().y < 0 || player.pos().y + player.height() > GAME_HEIGHT) {
            lost = true;
        }

        if (lost && !gameOverSoundPlayed) {
            SoundPlayer.playSoundEffect("/assets/audio/gameover.wav");
            gameOverSoundPlayed = true;
            SoundPlayer.stopBackgroundMusic();

            if (highScores == null) {
                fetchHighScores();
            }
        }

        if (lost && !scoreSubmitted && FlappyPanitz.playerNameForUpload != null && !FlappyPanitz.playerNameForUpload.isEmpty()) {
            submitScore();
            scoreSubmitted = true;
        }
    }

    @Override
    public void keyPressedReaction(KeyEvent keyEvent) {
        if (lost) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                init();
            }
            return;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
            player.flap(FLAP_STRENGTH);
            SoundPlayer.playSoundEffect("/assets/audio/jump.wav");
        }
    }

    @Override
    public boolean won() {
        // Naja, Flappy Bird gewinnen ist irgendwie schwierig? Bei einem endlos laufenden Spiel?
        return false;
    }

    @Override
    public boolean lost() {
        return lost;
    }

    @Override
    public void move() {
        if (ended()) return;

        Game.super.move();

        spawnCounter++;
        if (spawnCounter >= SPAWN_THRESHOLD) {
            spawnCounter = 0;
            spawnPipe();
        }
    }

    private void spawnPipe() {
        double minGapY = 50;
        double maxGapY = GAME_HEIGHT - PIPE_GAP - 50;
        double gapY = minGapY + Math.random() * (maxGapY - minGapY);
        Pipe newPipe = new Pipe(GAME_WIDTH, gapY, PIPE_WIDTH, PIPE_GAP, GAME_HEIGHT, PIPE_SPEED);
        pipes.add(newPipe);
    }

    @Override
    public void paintTo(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(0, 0, 1200, 800);

        Game.super.paintTo(g);

        g.setColor(Color.BLACK);

        g.setFont(pixel24PxFont);
        g.drawString("Score: " + score, 10, 30);
        if (lost) {
            g.setFont(pixel48PxFont);
            g.drawString("Game Over", GAME_WIDTH / 2 - 200, GAME_HEIGHT / 2 - 300);

            g.setFont(pixel24PxFont);
            g.drawString("ENTER zum Neustarten", GAME_WIDTH / 2 - 250, GAME_HEIGHT / 2 - 250);

            int tableX = GAME_WIDTH / 2 - 250;
            int tableY = GAME_HEIGHT / 2 - 150;
            g.drawString("Highscores:", tableX, tableY);
            if (highScoreErrorMessage != null) {
                g.setColor(Color.RED);
                g.drawString(highScoreErrorMessage, tableX, tableY + 50);
            } else if (highScores == null) {
                g.drawString("Highscores laden...", tableX, tableY + 50);
            } else {
                g.drawString("Name", tableX, tableY + 50);
                g.drawString("Score", tableX + 350, tableY + 50);
                int rowY = tableY + 100;
                for (HighScore hs : highScores) {
                    g.drawString(hs.player, tableX, rowY);
                    g.drawString(String.valueOf(hs.score), tableX + 350, rowY);
                    rowY += 50;
                }
            }
        }
    }

    private void fetchHighScores() {
        new Thread(() -> {
            try {
                URL url = new URL("https://flappypanitz.de/highscores.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                con.disconnect();

                // [ {"player": "Alice", "score": 100}, {"player": "Bob", "score": 80} ]
                JSONArray jsonArray = new JSONArray(content.toString());
                List<HighScore> scores = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String player = FlappyPanitz.truncateString(obj.getString("player"), 12);
                    int score = obj.getInt("score");
                    scores.add(new HighScore(player, score));
                }

                highScores = scores;
            } catch (Exception e) {
                System.err.println("Es ist ein Fehler beim Laden der Highscore-Daten aufgetreten: " + e.getLocalizedMessage());
                highScoreErrorMessage = "Es ist ein Fehler aufgetreten :(";
            }
            SwingUtilities.invokeLater(() -> {
                SwingScreen screen = SwingScreen.getInstance();
                if(screen != null) {
                    screen.repaint();
                }
            });
        }).start();
    }

    private void submitScore() {
        new Thread(() -> {
            try {
                long timestamp = System.currentTimeMillis();
                String dataToSign = score + ":" + FlappyPanitz.playerNameForUpload + ":" + timestamp + ":" + selectedCharacter.getName();

                PrivateKey privateKey = Signer.loadPrivateKey("/assets/key/private_key.pem");
                String signature = Signer.signData(dataToSign, privateKey);

                String postData = "score=" + URLEncoder.encode(String.valueOf(score), "UTF-8") +
                        "&player=" + URLEncoder.encode(FlappyPanitz.playerNameForUpload, "UTF-8") +
                        "&timestamp=" + URLEncoder.encode(String.valueOf(timestamp), "UTF-8") +
                        "&character=" + URLEncoder.encode(selectedCharacter.getName(), "UTF-8") +
                        "&signature=" + URLEncoder.encode(signature, "UTF-8");


                URL url = new URL("https://flappypanitz.de/submit.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                OutputStream os = con.getOutputStream();
                os.write(postData.getBytes("UTF-8"));
                os.flush();
                os.close();

                int responseCode = con.getResponseCode();
                System.out.println("Submit score response code: " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println("Submit score response: " + response);
            } catch (Exception e) {
                System.err.println("Fehler beim Senden der Daten: " + e.getLocalizedMessage());
            }
        }).start();
    }

}
