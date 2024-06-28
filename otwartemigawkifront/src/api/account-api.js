import axios from "axios";
export const fetchUserData = async (setUserData, setEmail,
  setName, setSurname, setPhone, setLoading) => {
  try {
    const info = await axios.get('/api/clients/get-user-info');
    setUserData(info.data);
    setEmail(info.data.email);
    setName(info.data.name);
    setSurname(info.data.surname);
    setPhone(info.data.phone);
    setLoading(false);
  } catch (error) {
    console.error('Error fetching info:', error);
  }
};