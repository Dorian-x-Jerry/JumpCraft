Dorian jan 5 1:25pm. created file

Jerry Jan 5 5:19pm. combined gravity and collision code to make rough demo of game jumping mechanics

Dorian jan 5 11:18 pm. tweaked gravity—collision with the top of walls will reduce yVelocity, instead of allowing the slime to essentially float. Found a bug: person will fall out of platforms—intended??

Dorian jan 6 12:02 am. fixed bug. collision physics should be all good now.

Dorian jan 7 12:04 am. Added winner() function and changed jump key to space. When you goto the very right of the screen. press E and it will check if you win. If not, it will print nothing. If you win, it will print ("you win")

Doiran jan 7 12:34 am. Added a background. Download the uploaded image and put it outside the src folder. Run the code and it should work.

Jerry Jan 7 6:25am. Created 3 separate levels under initialize method, added int level variable to track level number, set run method to stop when level reaches 4. To do: reset player location to bottom left at new level, create methods for title screen, win screen after winning level 3

Dorian Jan 7 1:28 pm. level changes & reseting position

Dorian Jan 10 12:11am. fixed collision, make the levels runable. For some reason, it needs to continously print "" to run. will fix later.

Jerry Jan 10 10:39am. made stylistic changes to keep consistency, moved mouse methods to top
