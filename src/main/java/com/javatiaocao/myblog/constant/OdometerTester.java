package com.javatiaocao.myblog.constant;

//Tracy He
//Oct 23rd
public class OdometerTester{
   public static void main(String[]args){
   
   Odometer ob1=new Odometer();
   Odometer ob2=new Odometer(100.0,10.0);
   
   //test getter
   System.out.println("object 1 have drived "+ob1.getMilesDriven()+" and mpg is "+ob1.getMPG());
   
   //test setter
   ob1.setMilesDriven(196);
   ob1.setMPG(50);
   System.out.println("object 1 have drived "+ob1.getMilesDriven()+" and mpg is "+ob1.getMPG());
   
   //test resetMilesDriven
   ob1.resetMilesDriven();
   System.out.println("object 1 have drived "+ob1.getMilesDriven()+" and mpg is "+ob1.getMPG());

   //test addmilesDriven
   ob1.addMilesDriven(3);
   System.out.println("object 1 have drived "+ob1.getMilesDriven()+" and mpg is "+ob1.getMPG());
   
   //test gallon used
   ob1.gallonsUsed();
   System.out.println("object 1 had used "+ob1.gallonsUsed()+" gallons");
   
   //to String
   System.out.println("Object1: "+ob1.toString()+"\nObject2: "+ob2.toString());
   
   //equal
   System.out.println("Does object1 has the same milesdriven and mpg? "+ob1.equals(ob2));
   ob1.setMilesDriven(100);
   ob1.setMPG(10);
   System.out.println("Does object1 has the same milesdriven and mpg? "+ob1.equals(ob2));
   
   //compareto
   if(ob1.compareTo(ob2)>0)
         System.out.println("object1 comes first");
      else if (ob1.compareTo(ob2)<0)
         System.out.println("object1 comes second");
      else 
         System.out.println("object1 and object2 come at the same time");
   }       
}
      

   
   


   

