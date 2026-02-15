import { useState, useEffect } from 'react'
import { getStudySessionsBySubjectId, deleteStudySession, updateStudySession } from '../services/api'

function StudySessionList({ subjectId, onSessionDeleted }) {
  const [sessions, setSessions] = useState([])
  const [editingId, setEditingId] = useState(null)
  const [editForm, setEditForm] = useState({})
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadSessions()
  }, [subjectId])

  const loadSessions = async () => {
    try {
      setLoading(true)
      const data = await getStudySessionsBySubjectId(subjectId)
      setSessions(data)
      setError(null)
    } catch (err) {
      setError('Failed to load sessions: ' + err.message)
    } finally {
      setLoading(false)
    }
  }

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this study session?')) {
      try {
        await deleteStudySession(id)
        loadSessions()
        onSessionDeleted()
        setError(null)
      } catch (err) {
        setError('Failed to delete session: ' + err.message)
      }
    }
  }

  const handleEdit = (session) => {
    setEditingId(session.id)
    setEditForm({
      durationMin: session.durationMin,
      notes: session.notes || ''
    })
  }

  const handleSaveEdit = async (id) => {
    try {
      await updateStudySession(id, {
        subjectId: subjectId,
        durationMin: parseFloat(editForm.durationMin),
        notes: editForm.notes
      })
      setEditingId(null)
      loadSessions()
      onSessionDeleted() // Refresh subjects to update total time
      setError(null)
    } catch (err) {
      setError('Failed to update session: ' + err.message)
    }
  }

  const handleCancelEdit = () => {
    setEditingId(null)
    setEditForm({})
  }

  const formatTimestamp = (timestamp) => {
    const date = new Date(timestamp)
    return date.toLocaleString('en-US', {
      month: 'short',
      day: 'numeric',
      year: 'numeric',
      hour: 'numeric',
      minute: '2-digit'
    })
  }

  const formatDuration = (minutes) => {
    const hours = Math.floor(minutes / 60)
    const mins = Math.round(minutes % 60)
    if (hours > 0) {
      return `${hours}h ${mins}m`
    }
    return `${mins}m`
  }

  if (loading) {
    return <div className="empty-state">Loading sessions...</div>
  }

  if (sessions.length === 0) {
    return <div className="empty-state">No study sessions yet. Create one to start tracking!</div>
  }

  return (
    <div className="session-list">
      {error && <div className="form-error">{error}</div>}
      {sessions.map((session) => (
        <div key={session.id} className="session-item">
          {editingId === session.id ? (
            <div className="form-card">
              <div className="form-group">
                <label>Duration (minutes)</label>
                <input
                  type="number"
                  step="0.1"
                  value={editForm.durationMin}
                  onChange={(e) => setEditForm({ ...editForm, durationMin: e.target.value })}
                />
              </div>
              <div className="form-group">
                <label>Notes</label>
                <textarea
                  value={editForm.notes}
                  onChange={(e) => setEditForm({ ...editForm, notes: e.target.value })}
                />
              </div>
              <div className="form-actions">
                <button className="btn-primary" onClick={() => handleSaveEdit(session.id)}>
                  Save
                </button>
                <button className="btn-secondary" onClick={handleCancelEdit}>
                  Cancel
                </button>
              </div>
            </div>
          ) : (
            <>
              <div className="session-header">
                <div className="session-info">
                  <div className="session-duration">
                    {formatDuration(session.durationMin)}
                  </div>
                  <div className="session-timestamp">
                    {formatTimestamp(session.timestamp)}
                  </div>
                </div>
                <div className="session-actions">
                  <button className="btn-edit" onClick={() => handleEdit(session)}>
                    Edit
                  </button>
                  <button className="btn-danger" onClick={() => handleDelete(session.id)}>
                    Delete
                  </button>
                </div>
              </div>
              {session.notes && (
                <div className="session-notes">
                  <strong>Notes:</strong> {session.notes}
                </div>
              )}
            </>
          )}
        </div>
      ))}
    </div>
  )
}

export default StudySessionList
