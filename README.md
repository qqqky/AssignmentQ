# AssignmentQ

 * Entry entry point to the program is the DisplayController class.
 * The only dependency required is SQLite JDBC library (exact version used is: sqlite-jdbc-3.32.3.2)
 * Program works on Java 8.
 * 
 * Make sure DB is initialized beforehand and is reachable for name "reg.db".
 * Empty blueprint database can easily be initialized by launching the main method of db/DbSetup.java class.
 * 
 * Class explanation:
 * 	app/DbModel is the main logic class, which is controlled by DisplayController.
 * 	CustomerClient, as the name says is the logic representation of CustomerGUIController
 * 	SpecialistClient is the same, but for SpecialistGUIController
 * 
 * Only DisplayController should have (direct) access to DbModel.
 * 
 * Input parameters in most cases aren't strictly checked (just enough to verify the GUI is working as intended).
 * There is also no bogus input control and any gibberish will be accepted as name/last name for customer registration,
 * 		as long as both of the fields are not completely empty.
 * To cancel a visit, customer must supply the code and his last name.
 * 
 * Additional info:
 * 		For simplicity reasons all passwords for the 5 registered specialists are: 12345
 * 		All usernames are their own names (see DbSetup class)
 * 		Example: login credentials of 1st specialist are: Amy 12345
 * 		Passwords are not hashed
 * 
 * 		GUI has "Spawn" button that spawns (registers) a random customer (name is Bobby+integer, last name is Bobson+integer - eg.:
 * 		Bobby0 Bobson0 will always be the first one spawned. Their code will be shown in the queue simulation.
 * 		
 * 		If program is restarted, codes are again started from value 000 (even though DB can already contain them).
 * 		Similarly, the random customer will also start from Bobby0 Bobson0 - the database is not checked for existing
 * 		entries for this (only allowed time is being checked).
 * 		+1/-1 buttons are used to increment/decrement the timer (eg. customer cannot cancel a visit if the visit is already
 * 		over (some margin of 'being late' is allowed).
 * 
 * 		Program will not register customers to time slots that are already (after some allowed error margin) in the past.
 * 
 * 		The easiest way to view the important contents of the DB is to login any specialist and click "Refresh List".
 * 
 * Not implemented:
 * 		Time management was not fully implemented due to time limitations on the project (the clock always starts from
 *  	8:55 - the beginning of the (nine-to-five) work day).
 *  	This time is updated every second (not shown) and is synchronized with the DbModel's class current time, 
 *  	at least until a specialist logs in.
 *  	Specialist's window should definitely contain a current time label as well (but it doesn't yet).
 *  	Specialist's window should also be keeping track of time (again, it doesn't)
 *  	State of the whole CustomerClient view is not retained and is not loaded from DB upon each launch,
 *  		it always starts from the blank slate.
 *  		
 *  		That means program currently assumes that, upon initializing the blueprint DB, the main client will be started
 *  		and will stay functioning for the whole work day 
 * 	
 * Therefore, given more time, the focus would be to implement full state recovery and complete communication with DB
 * on every aspect (including the clock).
