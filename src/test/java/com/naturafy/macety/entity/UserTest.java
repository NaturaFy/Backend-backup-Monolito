package com.naturafy.macety.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testGettersAndSetters() {
        User u = new User();
        u.setId(1L);
        u.setName("Juan");
        u.setEmail("juan@example.com");
        u.setPassword("Secret123");
        u.setAvatar("avatar.png");
        u.setLocation("Madrid");

        assertEquals(1L, u.getId());
        assertEquals("Juan", u.getName());
        assertEquals("juan@example.com", u.getEmail());
        assertEquals("Secret123", u.getPassword());
        assertEquals("avatar.png", u.getAvatar());
        assertEquals("Madrid", u.getLocation());
        assertNull(u.getCreatedAt());
    }

    @Test
    void testPrePersistSetsCreatedAt() {
        User u = new User();
        assertNull(u.getCreatedAt());
        u.onCreate(); // Simula @PrePersist
        assertNotNull(u.getCreatedAt());
    }

    @Test
    void testEqualsAndHashCodeSameValues() {
        User a = new User();
        a.setId(10L);
        a.setName("Ana");
        a.setEmail("ana@example.com");
        a.setPassword("Pass123");
        a.setAvatar("a.png");
        a.setLocation("Sevilla");
        a.onCreate();

        User b = new User();
        b.setId(10L);
        b.setName("Ana");
        b.setEmail("ana@example.com");
        b.setPassword("Pass123");
        b.setAvatar("a.png");
        b.setLocation("Sevilla");
        b.setCreatedAt(a.getCreatedAt());

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testNotEqualsDifferentEmail() {
        User a = new User();
        a.setId(11L);
        a.setName("Luis");
        a.setEmail("luis@example.com");

        User b = new User();
        b.setId(11L);
        b.setName("Luis");
        b.setEmail("otro@example.com");

        assertNotEquals(a, b);
    }

    @Test
    void testToStringNotEmpty() {
        User u = new User();
        u.setId(5L);
        u.setName("Test");
        u.setEmail("test@example.com");
        String s = u.toString();
        assertNotNull(s);
        assertFalse(s.trim().isEmpty());
    }
}
