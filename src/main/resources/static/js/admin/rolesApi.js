const API_URL = "http://localhost:8080/roles";

const getRoles = async () => {
    const response = await axios.get(API_URL);
    return response.data;
};

const addRole = async (roleData) => {
    await axios.post(API_URL, new URLSearchParams(roleData));
};

const deleteRole = async (roleId) => {
    await axios.delete(API_URL, {
        params: { id: roleId }
    });
};

const updateRole = async (roleData) => {
    await axios.put(API_URL, new URLSearchParams(roleData));
};
