# contended-java

CPU cores cache variables from the main memory in three different levels of cpu caches.
They do not store a single variable at a time but copy over a whole line of memory into the cache. The cache line is typically 64 bytes. So if a variable is smaller than 64 bytes, adjacent variables might also be copied over into the same line of a cache.

When one variable is updated by a core, any caches lines containing the variable are invalidated in caches maintained by other cores even if they don't use the variable. When it happens the other cores are forced to update the whole cache line from the main memory.

The use of @jdk.internal.vm.annotation.Contended annotation ensures that the variable doesn't share the cache line with other variable.

### The test

We have an interface SwitchBoard which has two implementations 

UnSharedSwitchBoard.java -> Contains two boolean flags each with their own cache line.
FalseSharedSwitchBoard.java -> Contains two boolean flags which should share a cache line.

You may need to add the following compile options to your module if you are using a later version of java  --add-exports java.base/jdk.internal.vm.annotation=ALL-UNNAMED

To run the main method in ContendedTest class with the flag -XX:-RestrictContended

The test flips the value of two boolean values,in two threads, one variable in each thread,  in each class 100000000 times. If the two boolean values share a cache line each time one is updated any core that has a cache line containing the variable has to refresh it even if the variable is not used 

Sample output

Testing false shared switchboard  
Total time for a 100000000 flips 4528 milli seconds 

====================================================  

Testing unshared switchboard  
Total time for a 100000000 flips 789 milli seconds  

====================================================  
There is a 7 times performance increase when the boolean variables don't have to share a cache line.

