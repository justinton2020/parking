import java.util.*;
import java.io.File;
import java.text.*;

// A UnitTest class has function testFile to read from text file and run 
class UnitTest {
    public void testFile(String fileTest) throws Exception {
        // block of code handle reading from a text file
        try{
            NumberFormat usFormat = NumberFormat.getCurrencyInstance(Locale.US);
            File  fl = new File(fileTest);
            Scanner sc = new Scanner(fl); 
            // read first line of text file is number of parking create
            Map<String, Car> listOfCar = new HashMap<String, Car>();
            int n = sc.nextInt();
            List<Parking> listOfParking = new ArrayList();;
            for (int i = 0; i < n; i++){
                int capa = sc.nextInt();
                char groupType = sc.next().charAt(0);
                float discountFee = sc.nextFloat();
                System.out.println("Create a garage with capacity of " + capa + " slots!");
                System.out.println("Group name: " + groupType + ". Discount for group: " + usFormat.format(discountFee) );
                Parking p = new Parking(capa, groupType, discountFee);
                listOfParking.add(p); 
            }
            // run a while loop to read all the lines until end of file
            while (sc.hasNext()){
                String str = sc.next();
                //System.out.println("str " + str);
                int num = sc.nextInt();
               // System.out.println(num);

                // check command whether is Parking or checkout a car.
                if (str.compareTo("Parking") == 0 ){
                    for (int i = 0; i < num; i++){
                        String a = sc.next();
                        char type = sc.next().charAt(0);
                        System.out.println("Parking " + a + " belong to group " + type);
                        Car c = new Car(a, type);
                        // garage park the car
                        c.parkMyCar(listOfParking);
                        if (!listOfCar.containsKey(a))
                            listOfCar.put(a, c);
                        if (c.isParkedYet() && listOfCar.containsKey(a)){
                            listOfCar.remove(a);
                            listOfCar.put(a, c);
                        }
                    }
                }
                if (str.compareTo("Checkout") == 0 ){
                    for (int i = 0; i < num; i++){
                        String b = sc.next();
                        System.out.println("Checkout " + b);
                        // garage checkout a car
                        if (listOfCar.containsKey(b)){
                            Car unit = listOfCar.get(b);
                            unit.checkOutMyCar();
                            listOfCar.remove(b);
                        }
                        else 
                            System.out.println("The car " + b + " is not parked yet!");
                        
                    }
                }
            }
            for (Parking p : listOfParking)
            {
                p.runReport();
            }
            sc.close();
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
    private Parking place;
    // store date and time of car
    private Date timeStamp;
    // id of a car: can be number or licence plate
    private String carId;
    private char carGroup;
    // contructor with id
    Car(String id, char group){
        ticket = false;
        timeStamp = null;
        carId = id;
        carGroup = group;
        place = null;
      
    }
    // method to park a car by checking an array of parking lot, it choose the lowest space parking
    // if all space are left it will print out
    public void parkMyCar(List<Parking> garage){
        int min = 1000;
        Map<Integer, Parking> list = new HashMap<Integer, Parking>();

        // a loop to check space of each parking. if space > 0 it will add to a list and record the smallest space.
        // if we have multiple min space it will add the first parking in the list
        for (int i = 0; i < garage.size() ; i++){
            Parking g = garage.get(i);
            if (!g.isParked(this))
            {
                
                if (g.checkSpace() > 0 ){
                    System.out.println("Parking " + g.getName() + " has " + g.checkSpace());
                    min = Math.min(min, g.checkSpace());
                    if (!list.containsKey(min))
                        list.put(min, g);
                }
            }
            else
            {
                System.out.println("The " + carId + " already parked at " + g.getName());
                return;
                
            }
        }
        // if list is empty that means no parking space left
        if (list.isEmpty())
        {
            System.out.println("The " + carId + " can't park because all Parking Lot is full\n");
        }
        // retrieve the parking object in the list using the minimun key and park this car object
        else 
        {
           
            place = list.get(min);
            System.out.println("the min space parking " + place.getName());
            place.parkCar(this);
            Date arrivalTime = new Date();
            parking(arrivalTime);
        
        }
        return;
    }

    public void checkOutMyCar(){
        System.out.println("Parked at " + place.getName());
        if (ticket == true){
           
            place.checkoutCar(carId);
           
        }
        else
        {
            System.out.println(ticket);
            System.out.println("The " + carId + " is not parked yet!\n");
        }
        return;
    }

    public char getGroup(){
        return carGroup;
    }


    // method parking with date time and change ticket to true
    public void parking(Date time){
        ticket = true;
        timeStamp = time;
        System.out.println("The " + carId + " is successfully parked at parking " + place.getName());
        System.out.println("Parked at: " + timeStamp+ "\n");
        return;
    }
    // method payticket is when car checkout of garage
    public void payTicket(){
        ticket = false;
        timeStamp = null;
        System.out.println("The " + carId + " group " + carGroup + " is successfully paid " + place.checkRate(carGroup) + " and leave the parking lot.");
        Date time = new Date();
        System.out.println("Checkouted at: " + time + "\n");
        return;
    }

// method checking car parking status - yes if ticket is true
    public boolean isParkedYet(){
        return ticket;
    }
 // get id(name) method
    public String getId(){
        return carId;
    }
    public int checkParkingSpace(Parking name){
        return name.checkSpace();
    }

}
public class Parking {
    float parkingFee = 2.50f;
    float discountFee;
    // the max number of lots of Parking lot can hold
    private int capacity;
    // the current lots are being used
    private int occupied;
    // a map list with key is name of car, value is Car object
    private Map<String, Car> listCar;
    int numberCarOfTheDay;
    private char groupName;
    private float totalDiscount;
    private float totalIncome;
    // construct a Parking garage with capacity
    Parking(int num, char name, float discount){
        capacity = num;
        totalDiscount = 0;
        totalIncome = 0;
        occupied = 0;
        groupName = name;
        this.discountFee = discount;
        numberCarOfTheDay = 0;
        // instantiate a list of Parked Car
        listCar = new HashMap();
    }

    public char getName(){
        return groupName;
    }
    public int checkSpace(){
        return capacity - occupied;
    }
    // method parkCar take Car object as parametter 
    public void parkCar(Car unit){
       
       
        occupied++;
        listCar.put(unit.getId(), unit);
        
       
        System.out.println("The garage " + Character.toString(groupName) + " has " + (capacity - occupied) + " slots left!\n");
        
     
    }
  
    public boolean isParked(Car unit){
        return listCar.containsKey(unit.getId());
    }
    // method to checkout car
    public void checkoutCar(String carId){
        // check if car is not in the list then state it is not parked yet
        
        // retrieve a car object in the car list and remove it
      
        Car unit = listCar.get(carId);
        
        collectParkingFee(unit);
       
        // call car payTicket method 
        unit.payTicket();
        
        // decrease occupied space by 1
        occupied--;
        numberCarOfTheDay++;
        listCar.remove(carId);
        System.out.println("The garage " + groupName +  " has " + (capacity - occupied) + " slots left!\n");
        return;
    }

    public float checkRate(char type){
        if (groupName == type)
            return discountFee;
        else 
            return parkingFee;
    }
    public void collectParkingFee (Car unit){
        if (groupName == unit.getGroup()){
            totalIncome += discountFee;
            totalDiscount += (parkingFee - discountFee);
        }
        else
            totalIncome += parkingFee; 
        return;
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
        System.out.println("Parking lot: " + groupName);
        System.out.println("The number of Checkouted Car  -  Total Income" );
        System.out.println("Cars: " + numberCarOfTheDay + "     -     "+ usFormat.format(this.totalIncome));
        System.out.println("The Total Discount: " +  usFormat.format(this.totalDiscount));
        System.out.println("================================");
        System.out.println("Number of Cars are still parking: " + this.occupied);
        System.out.println("=======END REPORT==================\n");
    }
    public static void main(String[] args) throws Exception
    {
      
       // Create a UnitTest object );
        UnitTest runTest = new UnitTest();
        // use testFile method of UnitTest class with (a Test filename)
        System.out.println("=============Test 1================");
        runTest.testFile("Test1.txt");
        // System.out.println("================================");
        // System.out.println("================================");
        // System.out.println("=============Test 2================");
        // runTest.testFile("Test2.txt");
        // System.out.println("================================");
        // System.out.println("================================");
        // System.out.println("=============Test 3================");
        // runTest.testFile("Test3.txt");
        // System.out.println("================================");
        // System.out.println("================================");
        // System.out.println("=============Test 4================");
        // runTest.testFile("Test4.txt");
        // System.out.println("================================");
        // System.out.println("================================");
        // System.out.println("=============Test 5================");
        // runTest.testFile("Test5.txt");
        
    }
}