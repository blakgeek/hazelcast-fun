# Battle Arena Example
This is a simple example showing how master failover can be handled.

This works based on two guarantees:

1. The membership events are always delivered in order.
1. The list of members in the event is always in a consistent order.

It is a very naive implementation but using Hazelcast seems to make it work.

### Build It
```
> mvn verify
```
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