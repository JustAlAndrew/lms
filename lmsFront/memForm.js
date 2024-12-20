document.getElementById('nextButton').addEventListener('click', async function () {
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const extensionName = document.getElementById('extensionName').value;
    const email = document.getElementById('email').value;
    const phoneNumber = document.getElementById('phoneNumber').value;
    const memberType = document.getElementById('memberType').value;


    if(!firstName || !lastName){
        alert( alert('First Name and Last Name are required fields. Please fill them in before proceeding.'));
        return;
    }

    const memberData = {
        firstName: firstName,
        lastName: lastName,
        extensionName: extensionName,
        email: email,
        phoneNumber: phoneNumber,
        memberType: memberType,
    };

    try {
        const response = await fetch('http://localhost:8080/api/members', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(memberData),
        });

        const responseMessage = document.getElementById('responseMessage');
        if (response.ok) {
            const member = await response.json(); // Parse the response to get the created member's data
            responseMessage.textContent = 'Base member created successfully!';
            responseMessage.classList.remove('error');

            // Save the member ID for further API calls
            const memberId = member.memberID;

            // Add additional fields dynamically based on the member type
            const additionalFields = document.getElementById('additionalFields');
            while (additionalFields.firstChild) {
                additionalFields.removeChild(additionalFields.firstChild);
            }

            // Set the flex-direction to column
                additionalFields.style.display = 'flex';
                additionalFields.style.flexDirection = 'column';

            // Helper function to create label-input pairs
            function createField(labelText, inputType, inputId, inputName) {
                const label = document.createElement('label');
                label.setAttribute('for', inputId);
                label.textContent = labelText;

                const input = document.createElement('input');
                input.setAttribute('type', inputType);
                input.setAttribute('id', inputId);
                input.setAttribute('name', inputName);
                input.required = true;

                additionalFields.appendChild(label);
                additionalFields.appendChild(input);
            }

            if (memberType === 'student') {
                createField('School Student ID:', 'number', 'schoolStudentID', 'schoolStudentID');
                createField('Course:', 'text', 'course', 'course');
                createField('Year Level:', 'number', 'yearLevel', 'yearLevel');
                createField('Department:', 'text', 'department', 'department');
            } else if (memberType === 'staff') {
                createField('Position:', 'text', 'position', 'position');
                createField('Shift:', 'text', 'shift', 'shift');
                createField('Staff Status:', 'text', 'staffStatus', 'staffStatus');
            }

            // Show the Submit button
            document.getElementById('nextButton').style.display = 'none';
            document.getElementById('submitButton').style.display = 'inline-block';

            // Add submit handler
            document.getElementById('submitButton').addEventListener('click', async function () {
                let additionalData = {};
                if (memberType === 'student') {
                    additionalData = {
                        schoolStudentID: document.getElementById('schoolStudentID').value,
                        course: document.getElementById('course').value,
                        yearLevel: document.getElementById('yearLevel').value,
                        department: document.getElementById('department').value,
                    };
                } else if (memberType === 'staff') {
                    additionalData = {
                        position: document.getElementById('position').value,
                        shift: document.getElementById('shift').value,
                        staffStatus: document.getElementById('staffStatus').value,
                    };
                }

                try {
                    const additionalResponse = await fetch(`http://localhost:8080/api/members/${memberId}/${memberType}`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(additionalData),
                    });

                    if (additionalResponse.ok) {
                        responseMessage.textContent = `${memberType.charAt(0).toUpperCase() + memberType.slice(1)} details added successfully!`;
                        responseMessage.classList.remove('error');
                    } else {
                        responseMessage.textContent = 'Failed to add additional details. Please try again.';
                        responseMessage.classList.add('error');
                    }
                } catch (error) {
                    console.error('Error:', error);
                    responseMessage.textContent = 'An error occurred while adding additional details.';
                    responseMessage.classList.add('error');
                }
            });
        } else {
            const errorData = await response.json();
            responseMessage.textContent = `Failed to create member: ${errorData.message || 'Please try again.'}`;
            responseMessage.classList.add('error');
        }
    } catch (error) {
        console.error('Error:', error);
        const responseMessage = document.getElementById('responseMessage');
        responseMessage.textContent = 'An error occurred. Please check your connection.';
        responseMessage.classList.add('error');
    }
});
