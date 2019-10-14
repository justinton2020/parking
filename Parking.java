import java.util.*;
import java.io.File;

import java.net.URL;

class UnitTest {
    public void testFile(String fileTest) throws Exception {
        File  fl = new File(fileTest);
        Scanner sc = new Scanner(fl); 
        int ca = sc.nextInt();
        System.out.println("Create a garage with capacity of " + ca + " slots!");
        Parking garage = new Parking(ca); 
        while (sc.hasNext()){
            String str = sc.next();
         //   System.out.println("str " + str);
            int num = sc.nextInt();
         //   System.out.println(num);
            if (str.compareTo("Parking") == 0 ){
                for (int i = 0; i < num; i++){
                    String a = sc.next();
                    System.out.println("Parking " + a);
                    Car c = new Car(a);
                    garage.parkCar(c);
                }
            }
            if (str.compareTo("Checkout") == 0 ){
                for (int i = 0; i < num; i++){
                    String b = sc.next();
                    System.out.println("Checkout " + b);
                    garage.checkoutCar(b);
                }
            }
        }
    }
}


class Car {
    // a boolean of ticket if false: not have ticket 
    // true: parking and have a ticket.
    boolean ticket;
    Date timeStamp;
    String carId;
    
    Car(String id){
        ticket = false;
        timeStamp = null;
        carId = id;
      
    }

    public void parking(Date time){
        ticket = true;
        timeStamp = time;
        System.out.println("The " + this.getId() + " is successfully parked.");
        System.out.println("Parked at: " + timeStamp+ "\n");
    }
    public void payTicket(){
        ticket = false;
        timeStamp = null;
        System.out.println("The " + this.getId() + " is successfully paid and leave the parking lot.");
        Date time = new Date();
        System.out.println("Checkouted at: " + time + "\n");
    }


    public boolean isParking(){
        return ticket;
    }
 
    public String getId(){
        return carId;
    }
}
public class Parking {
    // the max number of lots of Parking lot can hold
    private int capacity;
    // the current lots are being used
    private int occupied;
    private Map<String, Car> listCar;


    Parking(int num){
        capacity = num;
        occupied = 0;
        listCar = new HashMap();
    }

    public void parkCar(Car unit){
        if (listCar.containsKey(unit.getId())){
            System.out.println("The " + unit.getId() + " already parked!\n");
            return;
        }
        if (!isFull()){
        occupied++;
        Date arrivalTime = new Date();
        listCar.put(unit.getId(), unit);
        unit.parking(arrivalTime);
        System.out.println("The garage has " + (capacity - occupied) + " slots left!\n");
        }
        else 
            System.out.println("The " + unit.getId() + " can't park because Parking Lot is full\n");
    }


    public void checkoutCar(String carId){
        if (!listCar.containsKey(carId)){
            System.out.println("The " + carId + " is not parked yet!\n");
            return;
        }
        Car unit = listCar.get(carId);
        unit.payTicket();
        occupied--;
        System.out.println("The garage has " + (capacity - occupied) + " slots left!\n");
    }

    
    public boolean isFull(){
        if (occupied == capacity)
            return true;
        else
            return false;
    }

    public static void main(String[] args) throws Exception
    {
        // create list of car enter parking lot
       // URL path = Parking.class.getResource("Test1.txt");
        UnitTest runTest = new UnitTest();

        runTest.testFile("Test1.txt");
        // runTest.testFile("Test2.txt");
        // runTest.testFile("Test3.txt");
        // runTest.testFile("Test4.txt");
        // runTest.testFile("Test5.txt");
        
    }
}