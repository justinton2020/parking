import java.util.*;
import java.io.File;
import java.text.*;

// A UnitTest class has function testFile to read from text file and run 
class UnitTest {
    public void testFile(String fileTest) throws Exception {
        // block of code handle reading from a text file
        try{
            File  fl = new File(fileTest);
            Scanner sc = new Scanner(fl); 
            // read first line of text file
            int ca = sc.nextInt();
            System.out.println("Create a garage with capacity of " + ca + " slots!");
            Parking garage = new Parking(ca); 
            // run a while loop to read all the lines until end of file
            while (sc.hasNext()){
                String str = sc.next();
            //   System.out.println("str " + str);
                int num = sc.nextInt();
            //   System.out.println(num);

                // check command whether is Parking or checkout a car.
                if (str.compareTo("Parking") == 0 ){
                    for (int i = 0; i < num; i++){
                        String a = sc.next();
                        System.out.println("Parking " + a);
                        Car c = new Car(a);
                        // garage park the car
                        garage.parkCar(c);
                    }
                }
                if (str.compareTo("Checkout") == 0 ){
                    for (int i = 0; i < num; i++){
                        String b = sc.next();
                        System.out.println("Checkout " + b);
                        // garage checkout a car
                        garage.checkoutCar(b);
                    }
                }
            }
            sc.close();
            garage.runReport();
        }
        catch (Exception e) 
        {
            System.out.println("File not found");
        }
    }
}


class Car {
    // a boolean of ticket if false: not have ticket 
    // true: parking and have a ticket.
    private boolean ticket;
    // store date and time of car
    private Date timeStamp;
    // id of a car: can be number or licence plate
    private String carId;
    
    // contructor with id
    Car(String id){
        ticket = false;
        timeStamp = null;
        carId = id;
      
    }

    // method parking with date time and change ticket to true
    public void parking(Date time){
        ticket = true;
        timeStamp = time;
        System.out.println("The " + this.getId() + " is successfully parked.");
        System.out.println("Parked at: " + timeStamp+ "\n");
    }
    // method payticket is when car checkout of garage
    public void payTicket(){
        ticket = false;
        timeStamp = null;
        System.out.println("The " + this.getId() + " is successfully paid and leave the parking lot.");
        Date time = new Date();
        System.out.println("Checkouted at: " + time + "\n");
    }

// method checking car parking status - yes if ticket is true
    public boolean isParking(){
        return ticket;
    }
 // get id(name) method
    public String getId(){
        return carId;
    }
}
public class Parking {
    static float parkingFee = 2.50f;
    // the max number of lots of Parking lot can hold
    private int capacity;
    // the current lots are being used
    private int occupied;
    // a map list with key is name of car, value is Car object
    private Map<String, Car> listCar;
    int numberCarOfTheDay;
    int numberCarNotParked;
    

    // construct a Parking garage with capacity
    Parking(int num){
        capacity = num;
        occupied = 0;
        numberCarOfTheDay = 0;
        numberCarNotParked = 0;
        // instantiate a list of Parked Car
        listCar = new HashMap();
    }

    // method parkCar take Car object as parametter 
    public void parkCar(Car unit){
        // check condition if car is in the car list
        if (listCar.containsKey(unit.getId())){
            System.out.println("The " + unit.getId() + " already parked!\n");
            return;
        }
        // condtion if garage is not full then continue parking process
        if (!isFull()){
        occupied++;
        
        Date arrivalTime = new Date();
        // store car id in the list of parked car
        listCar.put(unit.getId(), unit);
        // park a car with its arrival time
        unit.parking(arrivalTime);
        // output the remaining slots of garage
        System.out.println("The garage has " + (capacity - occupied) + " slots left!\n");
        }
        // if garage is full , output message for user
        else {
            System.out.println("The " + unit.getId() + " can't park because Parking Lot is full\n");
            numberCarNotParked++;
        }
    }

    // method to checkout car
    public void checkoutCar(String carId){
        // check if car is not in the list then state it is not parked yet
        if (!listCar.containsKey(carId)){
            System.out.println("The " + carId + " is not parked yet!\n");
            return;
        }
        // retrieve a car object in the car list and remove it
        Car unit = listCar.get(carId);
        listCar.remove(carId);
       
        // call car payTicket method 
        unit.payTicket();
        // decrease occupied space by 1
        occupied--;
        numberCarOfTheDay++;
        System.out.println("The garage has " + (capacity - occupied) + " slots left!\n");
    }

    // check if garage is full or not
    public boolean isFull(){
        if (occupied == capacity)
            return true;
        else
            return false;
    }

    public void runReport(){
        NumberFormat usFormat = NumberFormat.getCurrencyInstance(Locale.US);

        System.out.println("===========REPORT==================");
        System.out.println("The number of Checkouted Car  -  Total Income" );
        System.out.println("Cars: " + numberCarOfTheDay + "     -     "+ usFormat.format(numberCarOfTheDay *parkingFee));
        System.out.println("================================");
        System.out.println("Number of Cars are still parking: " + occupied);
        System.out.println("Number of Car didn't park because of full slot: " + numberCarNotParked);
        System.out.println("=======END REPORT==================");
    }
    public static void main(String[] args) throws Exception
    {
      
       // Create a UnitTest object );
        UnitTest runTest = new UnitTest();
        // use testFile method of UnitTest class with (a Test filename)
        System.out.println("=============Test 1================");
        runTest.testFile("Test1.txt");
        System.out.println("================================");
        System.out.println("================================");
        System.out.println("=============Test 2================");
        runTest.testFile("Test2.txt");
        System.out.println("================================");
        System.out.println("================================");
        System.out.println("=============Test 3================");
        runTest.testFile("Test3.txt");
        System.out.println("================================");
        System.out.println("================================");
        System.out.println("=============Test 4================");
        runTest.testFile("Test4.txt");
        System.out.println("================================");
        System.out.println("================================");
        System.out.println("=============Test 5================");
        runTest.testFile("Test5.txt");
        
    }
}