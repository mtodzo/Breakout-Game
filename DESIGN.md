provides the high-level design goals of your project
explains, in detail, how to add new features to your project
justifies major design choices, including trade-offs (i.e., pros and cons), made in your project
states any assumptions or decisions made to simplify or resolve ambiguities in your the project's functionality

#Design Goals
* I wanted to create a two-player game which would integrate aspects of both Pong and Breakout
* I wanted to have a simple step method which provided a storyline for the game progression by having well-named methods
* I wanted the game to be easily modifiable with automatically generated levels
* I decided to use classes predominantly as a means for storing information about different objects that could be easily fetched and manipulated
* I chose to leave most of active methods in the main class only making calls to the other classes to obtain information

#New Features
Adding new features depends on the kind of feature. For a power-up, one must go into the Brick class and adjust the random number generation which assigns the specific power-up to the brick and increase the factor by one so that one more option can be included. Then, in the main class, the power-up execution code must be added. For cheat codes, one must go into the onKeyPressed method and add a new KeyCode and add the code to be executed when that key is pressed. Levels are generated on their own, and most other adjustments can be made through the global variables.

#Design Choices
The main choice I made was to use classes as a means for storing information rather than a means for actually executing code and taking active measures. This decision was made primarily on my understanding of the purposes of classes stemming from past projects, but after reading "OO in One Sentence" I realize that the tradeoff was that my code was far less organized, and less Shy since there were many dependencies. I would restructure my project to have more DIY (Do It Yourself) type classes so that code could be more organized with the objects it applied to. 