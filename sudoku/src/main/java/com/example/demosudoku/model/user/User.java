package com.example.demosudoku.model.user;

/**
 * Represents a Sudoku player.
 * Stores basic user information such as the nickname and optional score.
 */
public class User {

    /** The player's nickname. */
    private String nickname;

    /** Optional: The player's current score or completed games count. */
    private int score;

    /**
     * Constructs a new User with the given nickname.
     *
     * @param nickname The nickname chosen by the player.
     */
    public User(String nickname) {
        this.nickname = nickname.trim();
        this.score = 0; // Default score
    }

    /**
     * Returns the player's nickname.
     *
     * @return The nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the player's nickname.
     *
     * @param nickname The new nickname.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname.trim();
    }

    /**
     * Returns the player's score.
     *
     * @return The score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Updates the player's score.
     *
     * @param score The new score to assign.
     */
    public void setScore(int score) {
        this.score = Math.max(score, 0); // Prevent negative values
    }

    /**
     * Increases the player's score by a specified amount.
     *
     * @param points The number of points to add.
     */
    public void addScore(int points) {
        this.score += Math.max(points, 0);
    }

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", score=" + score +
                '}';
    }
}
