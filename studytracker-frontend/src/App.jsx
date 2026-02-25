import { BookOpen } from 'lucide-react'
import { useState, useEffect } from 'react'
import SubjectList from './components/SubjectList'
import SubjectForm from './components/SubjectForm'
import StudySessionList from './components/StudySessionList'
import StudySessionForm from './components/StudySessionForm'
import { getAllSubjects } from './services/api'

function App() {
  const [subjects, setSubjects] = useState([])
  const [selectedSubject, setSelectedSubject] = useState(null)
  const [showSubjectForm, setShowSubjectForm] = useState(false)
  const [showSessionForm, setShowSessionForm] = useState(false)
  const [error, setError] = useState(null)

  // NEW: reload trigger for StudySessionList
  const [sessionReloadTrigger, setSessionReloadTrigger] = useState(0)

  useEffect(() => {
    loadSubjects()
  }, [])

  const loadSubjects = async () => {
    try {
      const data = await getAllSubjects()
      setSubjects(data)
      setError(null)
    } catch (err) {
      setError('Failed to load subjects: ' + err.message)
    }
  }

  const handleSubjectCreated = () => {
    loadSubjects()
    setShowSubjectForm(false)
  }

  const handleSubjectDeleted = () => {
    loadSubjects()
    setSelectedSubject(null)
  }

  const handleSessionCreated = async () => {
  setSessionReloadTrigger(prev => prev + 1)
  await loadSubjects() 
  setShowSessionForm(false)
}

  const handleSessionDeletedOrUpdated = () => {
    setSessionReloadTrigger(prev => prev + 1) // reload after delete/edit
  }

  return (
    <div className="app">
      <header className="app-header">
       <div className="header-container">
          <h1 className="header-title">
            <BookOpen size={28} />
            Study Tracker
          </h1>
        </div>
      </header>


      {error && <div className="error-banner">{error}</div>}

      <div className="app-container">
        <div className="sidebar">
          <div className="section-header">
            <h2>Subjects</h2>
            <button 
              className="btn-primary"
              onClick={() => setShowSubjectForm(!showSubjectForm)}
            >
              {showSubjectForm ? 'Cancel' : '+ New Subject'}
            </button>
          </div>

          {showSubjectForm && (
            <SubjectForm 
              onSubjectCreated={handleSubjectCreated}
              onCancel={() => setShowSubjectForm(false)}
            />
          )}

          <SubjectList 
            subjects={subjects}
            selectedSubject={selectedSubject}
            onSelectSubject={setSelectedSubject}
            onSubjectDeleted={handleSubjectDeleted}
          />
        </div>

        <div className="main-content">
          {selectedSubject ? (
            <>
              <div className="section-header">
                <h2>Study Sessions for {selectedSubject.name}</h2>
                <button 
                  className="btn-primary"
                  onClick={() => setShowSessionForm(!showSessionForm)}
                >
                  {showSessionForm ? 'Cancel' : '+ New Session'}
                </button>
              </div>

              {showSessionForm && (
                <StudySessionForm 
                  subjectId={selectedSubject.id}
                  subjects={subjects}
                  onSessionCreated={handleSessionCreated} // reloads sessions
                  onCancel={() => setShowSessionForm(false)}
                />
              )}

              <StudySessionList 
                subjectId={selectedSubject.id}
                reloadTrigger={sessionReloadTrigger} // NEW: prop to trigger reload
                onSessionDeleted={handleSessionDeletedOrUpdated} // reload after delete
              />
            </>
          ) : (
            <div className="placeholder">
              <p>Select a subject to view study sessions</p>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}

export default App
