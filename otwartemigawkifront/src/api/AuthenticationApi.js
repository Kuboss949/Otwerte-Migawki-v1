import axios from 'axios';

export const fetchAuthenticationStatus = async (path) => {
  try {
    const response = await axios.get('/api/auth/' + path);
    return response.data;
  } catch (error) {
    console.error('Error fetching authentication status:', error);
    return false;
  }
};
  
export const fetchUserRole = async () => {
  try {
    const response = await axios.get('/api/auth/get-role');
    return response.data;
  } catch (error) {
    console.error('Error fetching user roles status:', error);
    return false;
  }
}