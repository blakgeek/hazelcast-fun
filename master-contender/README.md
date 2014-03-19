# Battle Arena Example
This is a simple example showing how we handle master failover.  It is a very nieve implementation
but using Hazelcast seems to make it work.

### Build It
```
mvn verify

### Run it
Start up a few instances
```
> java -jar target/contender.jar "The Shadow Lands" 3 2> /dev/null&
> java -jar target/contender.jar "The Shadow Lands" 3 2> /dev/null&
> java -jar target/contender.jar "The Shadow Lands" 3 2> /dev/null&
> java -jar target/contender.jar "The Shadow Lands" 3 2> /dev/null&
> java -jar target/contender.jar "The Shadow Lands" 3 2> /dev/null&
```
See whats running
```
> pgrep -f contender.jar
```
Kill a few instances
```
> kill <pid>
```