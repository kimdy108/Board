const apiMethod = 'http'
const apiUrl = window.location.hostname
const apiPort = import.meta.env.VITE_APP_API_PORT

export const baseUrl = `${apiMethod}://${apiUrl}:${apiPort}/api`
