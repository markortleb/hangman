# Hangman

## About
This is a simple Hangman game written in Java with Swing. This program consists of 3 different screens:
1. A start screen, that just displays a title and a start button (which takes you to next screen).
2. A screen to choose the category of the random word you will guess in the next screen. There are 3 
   buttons on this screen: movies, companies, and countries. Press any button and the user is redirected
   to the next screen.
3. The final screen is where the user will play the game. This screen has a few features:
    * Frames of hanging man will be progressively displayed on the left side, as user guesses wrong letters.
    * The bottom right side has a button with each letter. Buttons will be grayed and disabled after
      pressing. 
    * The top right side will have the area of phrase to be guessed. The word will initially be 
      comprised of underscores indicating a missing letter, and as user correctly guesses, the 
      underscores will be replaced with the actual letter from the phrase.
    * If users win or lose, the right side of this screen is replaced with a results panel, with message
      and buttons to Play Again, Choose Category, or Quit. 
