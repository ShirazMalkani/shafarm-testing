package com.raqami.shafarm.model;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    private User tester;

    private void setUser() {
        tester = new User();
        tester.setName("JUnit Test");
        tester.setEmail("test@email.com");
    }

    @Test
    public void testSettersAndGettersOfUserSuccess() {
        setUser();
        // assert statements
        assertEquals("Name must be equal to JUnit Test", "JUnit Test", tester.getName());
        assertEquals("Email must be equal to test@email.com","test@email.com", tester.getEmail());
    }

    @Test
    public void testToStringSuccess() {
        if(tester == null)
            setUser();
        User tempUser = new User("JUnit Test", "test@email.com");
        Gson gson = new Gson();
        assertEquals(gson.toJson(tempUser), tester.toString());
    }
}
