# coding-challenge

Coding Challenge Build Framework

### Project Layout

All source code should be located in the `src/main/java` folder.
If you wish to write any tests (not a requirement) they should be
located in the `src/test/java` folder.

A starter `Main.java` file has been provided in the `com/newrelic/codingchallenge` package under `src/main/java`.


### Dependencies

No dependencies in this project other than Junit for a few tests.


### Building your project from the command line

To build the project on Linux or MacOS run the command `./gradlew build` in a shell terminal.  This will build the source code in
`src/main/java`, run any tests in `src/test/java` and create an output
jar file in the `build/libs` folder.

To clean out any intermediate files run `./gradlew clean`.  This will
remove all files in the `build` folder.


### Running your application from the command line

You first must create a shadow jar file.  This is a file which contains your project code and all dependencies in a single jar file.  To build a shadow jar from your project run `./gradlew shadowJar`.  This will create a `codeing-challenge-shadow.jar` file in the `build/libs` directory.

You can then start your application by running the command
`java -jar ./build/lib/coding-challenge-shadow.jar`


## Assumptions

* Write access is granted to the directory the application is running by the user running the command
* The TERMINATE message would be transmitted in lower case, preventing unecessary String conversions/tests
* Closing the application is done via a SIGINT command/call, and did not have to be explicitly handled
* Something other than an in-memory data store would be used to store/manage the inputs, so a Repository interface has
been designed to support extensibility
* A test client was created to allow simple load testing of the socket server, and although it was assumed that it 
wasn't part of the challenge, it has been left in the project
* It would have been more preformant to process and compare the Numbers as Integers rather than Strings, but since 
Requirement #3 indicated that all Numbers would come with 9 digits including leading zeros it was deemed simpler and 
less error prone to keep them as Strings

## Performance Improvements

Originally i used a HashSet collection to manage the IDs in memory, and take advantage of the free de-duplication of Sets. 
This proved unscalable as the collection grew, because comparing and storing String or Integers is inefficient. A 
HashSet<Integer> collection began to crumble under its own weight after about 3 mins.

```
➜  coding-challenge-master java -jar ./build/libs/coding-challenge-shadow.jar
No valid port provided so using 4000.
	If you want to use a different port add a command
	line argument that is an integer above 1023.

Starting up reporter ....
Starting up server ....
Received 0 unique numbers, 0 duplicates. Unique total: 0
1 current connection
2 current connections
3 current connections
4 current connections
5 current connections
Received 2076826 unique numbers, 1422 duplicates. Unique total: 2076826
Received 3259075 unique numbers, 8346 duplicates. Unique total: 5336013
Received 3519794 unique numbers, 22293 duplicates. Unique total: 8855857
Received 1905089 unique numbers, 17853 duplicates. Unique total: 10761048
Received 1861143 unique numbers, 21229 duplicates. Unique total: 12622243
Received 2455705 unique numbers, 3379 duplicates. Unique total: 15077949
Received 2508938 unique numbers, 10451 duplicates. Unique total: 17586925
Received 1318610 unique numbers, 8342 duplicates. Unique total: 18905573
Received 2576342 unique numbers, 21813 duplicates. Unique total: 21481959
Received 2311428 unique numbers, 26162 duplicates. Unique total: 23793404
Received 1679471 unique numbers, 22483 duplicates. Unique total: 25472923
Received 2261565 unique numbers, 35891 duplicates. Unique total: 27734575
Received 2274721 unique numbers, 42228 duplicates. Unique total: 30009298
Received 165155 unique numbers, 3271 duplicates. Unique total: 30174507
Received 2408531 unique numbers, 51608 duplicates. Unique total: 32583108
Received 2203948 unique numbers, 52750 duplicates. Unique total: 34787087
Received 2334379 unique numbers, 62231 duplicates. Unique total: 37121489
Received 1612726 unique numbers, 46775 duplicates. Unique total: 38734267
Received 421176 unique numbers, 12665 duplicates. Unique total: 39155458
Received 421537 unique numbers, 12928 duplicates. Unique total: 39576995
Received 406100 unique numbers, 12487 duplicates. Unique total: 39983095
Received 386350 unique numbers, 12067 duplicates. Unique total: 40369534
```

When i refactored to a BitSet, which only stores bits at each location in the collection, the performance improved and 
stayed consistent.

```
➜  coding-challenge-master java -jar ./build/libs/coding-challenge-shadow.jar
No valid port provided so using 4000.
	If you want to use a different port add a command
	line argument that is an integer above 1023.

Starting up reporter ....
Starting up server ....
1 current connection
2 current connections
3 current connections
4 current connections
5 current connections
Received 20485 unique numbers, 1 duplicates. Unique total: 20485
Received 3288023 unique numbers, 6158 duplicates. Unique total: 3308511
Received 3399195 unique numbers, 18960 duplicates. Unique total: 6707976
Received 3469132 unique numbers, 32794 duplicates. Unique total: 10177176
Received 3431555 unique numbers, 46053 duplicates. Unique total: 13608782
Received 3432615 unique numbers, 59374 duplicates. Unique total: 17041464
Received 3456841 unique numbers, 73757 duplicates. Unique total: 20498347
Received 3424020 unique numbers, 86746 duplicates. Unique total: 23922370
Received 3449959 unique numbers, 101068 duplicates. Unique total: 27372374
Received 3332966 unique numbers, 111475 duplicates. Unique total: 30705357
Received 3157072 unique numbers, 117997 duplicates. Unique total: 33862485
Received 3254773 unique numbers, 133798 duplicates. Unique total: 37117304
Received 3249138 unique numbers, 146853 duplicates. Unique total: 40366468
Received 3161392 unique numbers, 154642 duplicates. Unique total: 43527939
Received 3228735 unique numbers, 170867 duplicates. Unique total: 46756707
Received 3153387 unique numbers, 179095 duplicates. Unique total: 49910191
Received 3105166 unique numbers, 188695 duplicates. Unique total: 53015359
Received 3187991 unique numbers, 206467 duplicates. Unique total: 56203395
Received 3187092 unique numbers, 217885 duplicates. Unique total: 59390531
Received 3189812 unique numbers, 231537 duplicates. Unique total: 62580347
Received 3164053 unique numbers, 243946 duplicates. Unique total: 65744453
```

These were run on a MacBook Pro; 2.4 GHz Intel Core i5, 8 GB 1600 MHz DDR3