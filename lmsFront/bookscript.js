const fetchBooks = async () => {
    try {
        const response = await fetch('http://localhost:8080/api/books');
        if (!response.ok) {
            throw new Error('Failed to fetch books');
        }
        const books = await response.json();

        // Debugging: Check fetched data
        console.log("Fetched books data:", books);

        renderBooks(books);
    } catch (error) {
        console.error('Error fetching books:', error);
    }
};

const renderBooks = (books) => {
    const container = document.getElementById('bookContainer');
    container.innerHTML = ''; // Clear previous content

    books.forEach((book) => {
        const bookBox = document.createElement('div');
        bookBox.classList.add('book-box');

        const titleElement = document.createElement('div');
        titleElement.classList.add('book-title');
        titleElement.textContent = book.title;
        bookBox.appendChild(titleElement);

        const authorElement = document.createElement('div');
        authorElement.classList.add('book-author');
        authorElement.textContent = `Author: ${book.author}`;
        bookBox.appendChild(authorElement);

        const categoryElement = document.createElement('div');
        categoryElement.classList.add('book-category');
        categoryElement.textContent = `Category: ${book.category}`;
        bookBox.appendChild(categoryElement);

        const statusElement = document.createElement('div');
        statusElement.classList.add('book-status');
        statusElement.textContent = `Available: ${book.available ? 'Yes' : 'No'}`;
        bookBox.appendChild(statusElement);

        const buttonGroup = document.createElement('div');
        buttonGroup.classList.add('button-group');

        // Borrow button
        const borrowButton = document.createElement('button');
        borrowButton.classList.add('borrow-btn');
        borrowButton.textContent = 'Borrow';
        borrowButton.onclick = () => openModal(book.bookID, 'borrow');
        borrowButton.disabled = !book.available;
        buttonGroup.appendChild(borrowButton);

        // Read button
        const readButton = document.createElement('button');
        readButton.classList.add('read-btn');
        readButton.textContent = 'Read';
        readButton.onclick = () => openModal(book.bookID, 'read');
        readButton.disabled = !book.available;
        buttonGroup.appendChild(readButton);

        // Return button
        const returnButton = document.createElement('button');
        returnButton.classList.add('return-btn');
        returnButton.textContent = 'Return';
        returnButton.onclick = () => openModal(book.bookID, 'return');
        returnButton.disabled = book.available;
        buttonGroup.appendChild(returnButton);

        bookBox.appendChild(buttonGroup);
        container.appendChild(bookBox);
    });
};


// Modal handling
const modal = document.getElementById('memberModal');
const modalSubmit = document.getElementById('submitModalMember');
const modalClose = document.getElementById('closeModal');
let currentActionType = null;
let currentBookId = null;

// Open the modal
const openModal = (bookId, actionType) => {
    currentActionType = actionType;
    currentBookId = bookId;
    modal.style.display = 'block';
};

// Close the modal
const closeModal = () => {
    modal.style.display = 'none';
    document.getElementById('modalFirstName').value = '';
    document.getElementById('modalLastName').value = '';
};

// Close modal on clicking the 'X'
modalClose.onclick = closeModal;

// Submit button handler inside modal
modalSubmit.onclick = async () => {
    const firstName = document.getElementById('modalFirstName').value.trim();
    const lastName = document.getElementById('modalLastName').value.trim();

    if (!firstName || !lastName) {
        alert('Both first name and last name are required.');
        return;
    }

    try {
        const memberResponse = await fetch(`http://localhost:8080/api/members/name?firstName=${encodeURIComponent(firstName)}&lastName=${encodeURIComponent(lastName)}`);
        
        if (!memberResponse.ok) {
            throw new Error('Member not found.');
        }

        const member = await memberResponse.json();
        const memberId = member.id;

        const actionPayload = { memberId, bookId: currentBookId };
        let endpoint;

        if (currentActionType === 'borrow') {
            endpoint = 'http://localhost:8080/api/library/borrow';
        } else if (currentActionType === 'read') {
            endpoint = 'http://localhost:8080/api/library/read';
        } else if (currentActionType === 'return') {
            endpoint = 'http://localhost:8080/api/library/return';
        } else {
            throw new Error("Invalid action type.");
        }

        const actionResponse = await fetch(endpoint, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(actionPayload),
        });

        if (!actionResponse.ok) {
            throw new Error('Failed to perform action.');
        }

        const result = await actionResponse.text();
        alert(result);
        fetchBooks();
        closeModal();
    } catch (error) {
        alert(`Error: ${error.message}`);
    }
};

// Function to search for books
const searchBooks = async () => {
    const searchInput = document.getElementById('searchInput').value.trim(); // Get user input
    if (!searchInput) {
        alert('Please enter a search term.');
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/books/search/title?title=${encodeURIComponent(searchInput)}`);
        if (!response.ok) {
            throw new Error('Failed to fetch search results');
        }
        const books = await response.json();
        renderBooks(books); // Render the search results
    } catch (error) {
        console.error('Error searching books:', error);
    }
};

// Function to reset the search and show all books
const resetSearch = async () => {
    document.getElementById('searchInput').value = ''; // Clear the search input
    await fetchBooks(); // Reload all books
};

// Fetch and display books on page load
fetchBooks();


