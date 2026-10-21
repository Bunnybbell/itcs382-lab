ConsoleMenu.java, CsvRideStore.java, FeeCalculator.java, MemberType.java, Rental.java, Ride.java, RideStore.java, Station.java (11 uploaded Java files).
1. Code Review Findings
No.	Check	Code location (file:line)	Finding	Severity	Suggested fix
1	Code quality	FeeCalculator.java:10	A null MemberType is treated as STAFF because the ternary expression uses the staff rate for any value other than STUDENT.	Should fix	Validate that the member type is not null before calculating the fee.
2	Code quality	BikeShareService.java:22–33	The rent() method does not validate null member types or stations, which may cause incorrect fees or a NullPointerException.	Should fix	Validate the member type and station before taking a bike.
3	Code quality	BikeInventory.java:23–24, 31–32	Passing a null station causes a NullPointerException when accessing the station's bike collection.	Should fix	Validate the station argument before accessing the map.
4	Code quality	ConsoleMenu.java:76–78	The ask() method calls nextLine() without checking whether input is available, so EOF during an operation can terminate the program unexpectedly.	Should fix	Check hasNextLine() and handle EOF before reading input.
5	Code quality	ConsoleMenu.java:35–38, 58–59	Math.multiplyExact() can throw ArithmeticException for very large ride times, but the menu does not catch this exception.	Note	Handle ArithmeticException or validate the maximum permitted ride duration.
6	Design quality – Encapsulation	BikeInventory.java:31–32	returnBike() accepts any bike ID without checking whether the bike belongs to the inventory, allowing invalid or duplicate bike IDs to be added.	Should fix	Track valid bike IDs and reject invalid or duplicate returns.
7	Design quality – Coupling	BikeShareService.java:48–51, CsvRideStore.java:30–33	Passing the complete Ride object to the persistence layer is stamp coupling because the receiving class accesses multiple fields of the record.	Note	Pass only the required ride data through a narrower interface if reducing stamp coupling is necessary.
8	Design quality – Coupling	ConsoleMenu.java:61–67	The console receives a complete Ride object and directly accesses its fields, creating stamp coupling between the menu and the ride data structure.	Note	Consider returning a dedicated receipt representation to separate presentation requirements from the ride model.

2. Cohesion, Coupling and Connascence Checks
Design check	Result
Methods with procedural, temporal, logical or coincidental cohesion	Not found
Classes with clearly unrelated responsibilities	Not found
Control, common or content coupling between classes	Not found
Classes reaching through objects to access other objects	Not found
Excessive class dependencies	Not found
Avoidable interclass connascence requiring a separate finding	Not found
Non-private fields	Not found
Internal collections exposed for modification	Not found

3. Number of Findings
Check	Findings
Check 1 – Code quality	5
Check 2 – Cohesion, coupling and connascence	3
Total	8