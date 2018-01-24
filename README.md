game
====

First project for CompSci 308 Fall 2017

This was an individual assignment which I began January 17th and completed on January 21st. It took about 11 or 12 hours.

I included the cheat codes on the introductory screen of the game, but they are:
Q/P - initiates Catch and Release (Q for player 1, P for player 2)
E/O - paddle takes up the entire side (invincible)
SPACE - increase ball speed

Known bugs:
Occasionally, when trying to use the Catch and Release cheat codes, the executions are flipped so that P initiates it for the left paddle
and Q for the right paddle. This issue arises sometimes only after getting a power-up or using the cheat keys to change the height of the paddle.
I think the issue stems from removing and then creating a new Paddle in these cases and possibly switching which paddle is paddle1 vs paddle2. I am still looking for the exact spot of the error. The other bugs stem from using a step function to model a continuous game. For example, if one uses the keys to move the paddles off the walls, it is possible for them to get the ball stuck in the paddle, or if the ball begins going too fast, it can go through the paddle.

I enjoyed this project, especially the challenge of designing it without any real guidelines or instructions. I realize now that I should have made more classes (i.e. classes for tough and power bricks that extend the Brick class), but I think this will help me as I progress through the class. 