package com.javatiaocao.myblog.constant;//Tracy He
//Oct 23rd

public class TempTester{
   public static void main(String[] args){
   
   Temperature t1=new Temperature ();
   Temperature t2=new Temperature (77.0,'F');
   
   //test setter
   t1.setScale('C');
   t1.setTemp(33);
   t1.getC();
//   Temperature.getC();
   Temperature.getD();
      t1.getC();
//      TemperatureS temperatureS = new TemperatureS;
//      temperatureS.getC();

      //test getC
   System.out.println("the degree of tempreture one in C is "+t1.getC());
   System.out.println("the degree of tempreture two in C is "+t2.getC());
   
   //test getF
   System.out.println("the degree of tempreture one in F is "+t1.getF());
   System.out.println("the degree of tempreture two in F is "+t2.getF());
   
   
   //test to String
   System.out.println(t1.toString());
   System.out.println(t2.toString());
   
   //test equal
   System.out.println("Does t1 == t2? "+t1.equals(t2));
   t2.setTemp(91.4);//重載錢的方法
      t2.setTemp(91.4,2);
   System.out.println(t2.toString());
   System.out.println("Does t1 == t2? "+t1.equals(t2));


 
   }
}
