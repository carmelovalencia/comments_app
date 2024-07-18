Note taker application

This is a simple Springboot Microservice which provides CRUD operations of a note (having a title and body).
The microservice is using H2 in memory database.

Java version: 1.8

1. Using a command prompt in Windows, run the application using this command "mvn spring-boot:run"
2. See notetaker.postman_collection to test the following apis using Postman

	· POST /notes: Create a new note.
	· GET /notes: Retrieve all notes.
	· GET /notes/:id: Retrieve a specific note by ID.
	· PUT /notes/:id: Update a specific note.
	· DELETE /notes/:id: Delete a specific note.
	
