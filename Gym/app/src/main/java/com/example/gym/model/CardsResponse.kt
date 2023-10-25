package com.example.gym.model

class CardsResponse(val cards: List<Card>? = null) {}

class Card(val name: String, val manaCost: String, val imageUrl: String) {}

