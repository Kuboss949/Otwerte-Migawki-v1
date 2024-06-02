import axios from "axios";

function mapSessions(inputArray) {
    const result = inputArray.reduce((acc, item) => {
        const clientKey = `${item.clientName} ${item.clientSurname} (${item.clientPhone})`;
        if (!acc[clientKey]) {
            acc[clientKey] = {
                client: clientKey,
                sessions: []
            };
        }
        acc[clientKey].sessions.push({ id: item.id, sessionTypeName: item.sessionTypeName, date: item.date });
        return acc;
    }, {});

    return Object.values(result);
}

export const fetchUserData = async (setMergedData,
    setClientsList, setLoading) => {
    try {
        const data = await axios.get('/api/session/all-without-galleries');
        const mappedData = mapSessions(data.data);
        setMergedData(mappedData);
        setClientsList(mappedData.map(data => data.client));
    } catch (error) {
        console.error('Error fetching data:', error);
    } finally {
        setLoading(false);
    }
};