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

Using a look-up table was obviously not the best way to implement a tic-tac-toe AI, though. Any player that knew the order in which the AI looks up moves would know exactly how to win every time, guaranteed. As a result, my next iteration involved creating an AI that could select its next move on its own, without a look-up table. This involved implementing a minimax algorithm, which aims to *minimize* the *maximum* loss. How is this achieved? First, I used an evaluation function to determine the score of the tic-tac-toe board after any given move. The rules were as follows:

* ADD 100 points for each of the AI's 3-in-a-rows
* ADD 10 points for each of the AI's 2-in-a-rows with an empty cell in the row
* ADD 1 point for each of the AI's 1-in-a-rows with two empty cells in the row
* SUBTRACT the resulting points from evaluating the board the same way from the player's point of view
* DO NOTHING for empty lines or lines that are filled with both the AI's and the player's marks

### Go Fish

### Battleship

### Explorations & Other Lessons Learned


### Conclusion
