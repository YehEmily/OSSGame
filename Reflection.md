# A Reflection and Explanation of the Games
*(Note: This will be a work in progress, probably until the end of the semester)*

I started this semester (Spring 2017) knowing next to nothing about artificial intelligence. I regarded it the way I regard things like rockets and Rubik's Cubes (and their [variations](https://www.quora.com/Complexity-What-is-the-most-complicated-thing-ever)); something unquestionably complicated and but also super cool. I also had only just picked up Java as a programming language the semester before, but since I had somehow, miraculously landed an internship for the summer in Java programming (at [Pivotal Labs](https://pivotal.io/labs)), I decided that I needed a way to keep improving my Java know-how. As a result, I proposed this independent study (an Olin Self Study, or OSS) in artificial intelligence to my instructor, Oliver Steele, who agreed to be my advisor.

The first few weeks of the semester were spent reading chapters from the renowned AI textbook, *Artificial Intelligence: A Modern Approach*, by Russell and Norvig, a book that I had chosen on my own. So it was entirely my fault that, although it was a helpful and thorough text, I eventually decided that it was too dry for me. Oliver, my fellow OSS advisee Dakota Nelson, and I met every Tuesday; every Sunday (since I had classes on Monday), I would cram two or three chapters' worth of work into my portfolio. Throughout the week, I dreaded the weekend, because I always put off the readings until then. After about a month of reading from the textbook and cramming, I realized that I hated how the OSS was going. But, as a friend pointed out to me one day, it didn't have to be this way. I wanted to learn about artificial intelligence, and as a student at Olin, what better way could I learn them than through practical application? This was when I proposed a change of plans to Oliver (who approved): I decided that instead of using the textbook as my main mode of learning, I would create a suite of games with accompanying AIs, and perhaps refer to the textbook whenever I got stuck.

### Tic-Tac-Toe
The first game I set out to create was tic-tac-toe. It's a game that almost everyone is familiar with; given a 3x3 grid, try to get three marks in a row to win. As can be expected, though, the game is slightly more complicated when trying to implement an AI that can play it.

In my first iteration of the game, I fed the AI a look-up table. It's *the simplest possible way to write an agent program*, as the textbook says. The look-up table I used can be seen stored as the variable `moves` below.

```java
// Moves in order of preference, where {0,0} is the top left.
// {1,1} (center) is the most preferred move because it allows for the most possible future moves
int[][] moves = {{1,1}, {0,0}, {0,2}, {2,0}, {2,2}, {0,1}, {1,0}, {1,2}, {2,1}};
```

Using a look-up table was obviously not the best way to implement a tic-tac-toe AI, though. Any player that knows the order in which the AI looks up moves will also know exactly how to win every time, guaranteed. As a result, my next iteration involved creating an AI that could select its next move on its own, without a look-up table. This involved implementing a minimax algorithm, which aims to *minimize* the *maximum* loss. How is this achieved? First, I used an evaluation function to determine the score of the tic-tac-toe board after any given move. The rules were as follows:

* ADD 100 points for each of the AI's 3-in-a-rows
* ADD 10 points for each of the AI's 2-in-a-rows with an empty cell in the row
* ADD 1 point for each of the AI's 1-in-a-rows with two empty cells in the row
* SUBTRACT the resulting points from evaluating the board the same way from the player's point of view
* DO NOTHING for empty lines or lines that are filled with both the AI's and the player's marks

Then, for every move that the player makes, the AI generates trees of every possible move that it could make, then every possible subsequent move the player could make, and so on until an endgame state is reached. An endgame state is defined as either the point where there are no moves left, or the point at which either the player or the AI has won the game. For each state that was generated, the board is evaluated using the rules above. The AI finally makes a move based on the series of moves that results in the highest overall score.

In this way, it was possible for my tic-tac-toe AI to win every single game, or end every single game in a tie. To make the game more fun, I decided to "dumb down" the AI a bit and let it only look ahead by a few moves, instead of seeing every possibility.

### Go Fish
Go Fish was a game that I started throwing together over winter break, and finished during this OSS. Its AI algorithm is relatively simple. Every time the player asks for a card, the AI "remembers" that card and stores it in its memory. If it happens to have the card in a later turn, it will ask the player for that same card. However, if the user makes a match with that card before then, the AI discards that card from its memory.

### Battleship
Battleship was the last game that I wrote. There were about 2.5 different AI algorithms that I used. The first simply involved making random shots, ignoring randomly generated coordinates that were already marked as misses or hits. When a hit was made, this algorithm also generated a list of possible other, neighboring coordinates to the hit, so that it would shoot at a hit's neighbors after finding a hit. The second algorithm was slightly more complicated; it involved generating a probability distribution function (PDF) that allows the AI to pick the most likely coordinate to shoot at, based on how many different ways a ship could fit in the given space. The final algorithm was an adaptation of the PDF algorithm, in that it didn't just calculate the probability based on whether a single ship could fit in the given space, but rather, it calculated the probability based on how many of the remaining ships could fit in the given space. This was actually the first time I had heard of this strategy for playing Battleship; I never knew that using a PDF algorithm could lower the number of shots necessary by a significant number, but as it turned out, when a randomly-shooting AI played against a PDF-based AI, the PDF-based AI won every time.

### Explorations & Other Lessons Learned
* [Codacy](https://www.codacy.com): A really amazing code review application that taught me so much about programming conventions. Now that I know about it, I really want to recommend it to everyone I know - it's free, flexible (allows users to define, enable, and disable "patterns" that are caught by the application), and gives astoundingly comprehensive diagnoses of code. It also integrates with a wide variety of different developer tools - most notably, GitHub and Heroku. Thanks to Oliver for introducing it to me!
* .gitignore files: So, before this project, I didn't know what .gitignore files were, but now I do! They are files that contain lists of files that the user would like GitHub to ignore (when they try to use commands like "git add ." etc.). I found the .gitignore file especially useful in this project for ignoring all of the *.class files that are generated every time I run a program.


### Conclusion
