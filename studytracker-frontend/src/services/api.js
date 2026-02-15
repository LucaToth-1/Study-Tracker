import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Subject API calls
export const getAllSubjects = async () => {
  const response = await api.get('/subjects')
  return response.data
}

export const getSubjectById = async (id) => {
  const response = await api.get(`/subjects/${id}`)
  return response.data
}

export const createSubject = async (subjectData) => {
  const response = await api.post('/subjects', subjectData)
  return response.data
}

export const updateSubject = async (id, subjectData) => {
  const response = await api.put(`/subjects/${id}`, subjectData)
  return response.data
}

export const deleteSubject = async (id) => {
  const response = await api.delete(`/subjects/${id}`)
  return response.data
}

// Study Session API calls
export const getAllStudySessions = async () => {
  const response = await api.get('/sessions')
  return response.data
}

export const getStudySessionById = async (id) => {
  const response = await api.get(`/sessions/${id}`)
  return response.data
}

export const getStudySessionsBySubjectId = async (subjectId) => {
  const response = await api.get(`/sessions/subject/${subjectId}`)
  return response.data
}

export const createStudySession = async (sessionData) => {
  const response = await api.post('/sessions', sessionData)
  return response.data
}

export const updateStudySession = async (id, sessionData) => {
  const response = await api.put(`/sessions/${id}`, sessionData)
  return response.data
}

export const deleteStudySession = async (id) => {
  await api.delete(`/sessions/${id}`)
}

export default api
