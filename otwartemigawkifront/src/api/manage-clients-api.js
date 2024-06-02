import axios from "axios";
export const fetchClientsData = async (setClients) => {
    try {
        const clientsData = await axios.get('/api/clients/all');
        setClients(clientsData.data);
    } catch (error) {
        console.error('Error fetching clients:', error);
    }
};

export const deleteUser = async (userId, setClients) => {
    try {
        await axios.delete(`/api/clients/${userId}`);
        const clientsData = await axios.get('/api/clients/all');
        setClients(clientsData.data);
    } catch (error) {
        console.error('Error deleting client:', error);
    }
};
