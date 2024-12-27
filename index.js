document.getElementById('timeForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent default form submission

    // Get values from the form
    const studentId = document.getElementById('studentId').value;
    const role = document.getElementById('role').value;
    const action = document.getElementById('action').value;

    // Check if all form fields are filled
    if (!studentId || !role || !action) {
        alert('Please fill out all fields.');
        return;
    }

    // Create the request body object
    const requestBody = {
        studentId: studentId,
        role: role,
        action: action
    };

    // Determine the correct URL based on the action
    const url = action === 'Time In' 
        ? 'http://localhost:8080/api/attendance/time-in'  // Full URL with localhost and port
        : 'http://localhost:8080/api/attendance/time-out'; // Full URL with localhost and port

    // Send POST request to Spring Boot controller using fetch API
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    })
    .then(response => response.json())
    .then(data => {
        // Display the response in the output section
        document.getElementById('output').innerHTML = `Success: ${JSON.stringify(data)}`;
    })
    .catch(error => {
        // Handle errors
        document.getElementById('output').innerHTML = `Error: ${error}`;
    });
});
