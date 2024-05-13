export const isValidEmail = (email) => {
    const emailRegex = /^[a-zA-Z0-9_+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/;
    return emailRegex.test(email);
};

export const isValidPhoneNumber = (phoneNumber) => {
    // Check if the string contains only digits and has at least 9 characters
    return /^\d{9,}$/.test(phoneNumber);
};

export const isValidSurname = (surname) => {
    // Check if the value is not empty
    return surname !== null && surname.trim() !== '';
};

export const isValidName = (name) => {
    // Check if the value is not empty
    return name !== null && name.trim() !== '';
};

export const isValidPassword = (password) => {
    // Check if the password has at least 8 characters and contains at least one uppercase letter, one lowercase letter, and one digit
    return password.length >= 8 &&
        /[A-Z]/.test(password) &&
        /[a-z]/.test(password) &&
        /\d/.test(password);
};