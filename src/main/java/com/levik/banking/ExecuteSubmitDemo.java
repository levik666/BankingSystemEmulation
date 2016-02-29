package com.levik.banking;

import com.levik.banking.service.CustomThreadPoolExecutor;

public class ExecuteSubmitDemo{
    public ExecuteSubmitDemo()
    {
        System.out.println("creating service");
        CustomThreadPoolExecutor service = new CustomThreadPoolExecutor();
        service.submit(new Runnable(){
            public void run(){
                int a=4, b = 0;
                System.out.println("a and b="+a+":"+b);
                System.out.println("a/b:"+(a/b));
                System.out.println("Thread Name in Runnable after divide by zero:"+Thread.currentThread().getName());
            }
        });
        //service.shutdown();
    }
    public static void main(String args[]){
        ExecuteSubmitDemo demo = new ExecuteSubmitDemo();
    }
}
