package com.karthik;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.exceptions.LockerNotFoundException;
import com.karthik.model.OrderType;
import com.karthik.model.Package;
import com.karthik.service.LockerService;

/**
 * Unit test for Locker Management system.
 */
public class AppTest 
{
    @Test
    public void testLockerManagementSystem()
    {
        // create locker service
        LockerService service = new LockerService();
        
        // add lockers
        service.addLocker(1, 10);
        service.addLocker(2, 4);
        service.addLocker(3, 1);
        service.addLocker(4, 17);
        service.addLocker(5, 9);
        service.addLocker(6, 100);

        assertEquals(service.getPoolSize(), 6);

        // create order packages
        Package order1 = new Package("yoga mat", 27, "user1@gmail.com");
        Package order2 = new Package("coffee kettle", 6, "user2@gmail.com");
        Package order3 = new Package("curtains", 17, "user3@gmail.com");
        Package order4 = new Package("tea kettle", 6, "user4@gmail.com");

        // place orders for 2 and 3 delivery
        int lockerID = service.allocateLocker(order2, OrderType.DELIVERY);   // otp = 000000
        assertEquals(5, lockerID);
        assertEquals(service.getLockerById(lockerID).getCurrentOTP(), "000000");

        lockerID = service.allocateLocker(order4, OrderType.DELIVERY);      // otp = 000001
        assertEquals(1, lockerID);
        assertEquals(service.getLockerById(lockerID).getCurrentOTP(), "000001");

        lockerID = service.allocateLocker(order3, OrderType.DELIVERY);      // otp = 000002
        assertEquals(4, lockerID);
        assertEquals(service.getLockerById(lockerID).getCurrentOTP(), "000002");

        lockerID = service.allocateLocker(order1, OrderType.DELIVERY);      // otp = 000003
        assertEquals(6, lockerID);
        assertEquals(service.getLockerById(lockerID).getCurrentOTP(), "000003");

        // orders 2 & 3 is picked up by customer
        service.unlockLocker(5, "000000");
        assertFalse(service.getLockerById(5).isAllocated());

        service.unlockLocker(4, "000002");
        assertFalse(service.getLockerById(4).isAllocated());

        // verify return flow, locker 4 should be allocated for order3
        lockerID = service.allocateLocker(order3, OrderType.RETURN);
        assertEquals(4, lockerID);
        assertEquals(service.getLockerById(lockerID).getCurrentOTP(), "000004");

        // verify no locker available
        Package order5 = new Package("dozer", 101, "user5@gmail.com");
        assertThrows(LockerNotFoundException.class, () -> service.allocateLocker(order5, OrderType.DELIVERY));
    }
}
