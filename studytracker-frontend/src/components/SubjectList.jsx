import { useState } from 'react'
import { deleteSubject, updateSubject } from '../services/api'

function SubjectList({ subjects, selectedSubject, onSelectSubject, onSubjectDeleted }) {
  const [editingId, setEditingId] = useState(null)
  const [editName, setEditName] = useState('')
  const [error, setError] = useState(null)

  const handleDelete = async (id, e) => {
    e.stopPropagation()
    if (window.confirm('Are you sure you want to delete this subject?')) {
      try {
        await deleteSubject(id)
        onSubjectDeleted()
        setError(null)
      } catch (err) {
        setError('Failed to delete subject: ' + err.message)
      }
    }
  }

  const handleEdit = (subject, e) => {
    e.stopPropagation()
    setEditingId(subject.id)
    setEditName(subject.name)
  }

  const handleSaveEdit = async (id, e) => {
    e.stopPropagation()
    try {
      await updateSubject(id, { name: editName })
      setEditingId(null)
      onSubjectDeleted() // Refresh the list
      setError(null)
    } catch (err) {
      setError('Failed to update subject: ' + err.message)
    }
  }

  const handleCancelEdit = (e) => {
    e.stopPropagation()
    setEditingId(null)
  }

  const formatDuration = (minutes) => {
    const hours = Math.floor(minutes / 60)
    const mins = Math.round(minutes % 60)
    if (hours > 0) {
      return `${hours}h ${mins}m`
    }
    return `${mins}m`
  }

  if (subjects.length === 0) {
    return <div className="empty-state">No subjects yet. Create one to get started!</div>
  }

  return (
    <div className="subject-list">
      {error && <div className="form-error">{error}</div>}
      {subjects.map((subject) => (
        <div
          key={subject.id}
          className={`subject-item ${selectedSubject?.id === subject.id ? 'selected' : ''}`}
          onClick={() => onSelectSubject(subject)}
        >
          <div className="subject-item-header">
            {editingId === subject.id ? (
              <input
                type="text"
                value={editName}
                onChange={(e) => setEditName(e.target.value)}
                onClick={(e) => e.stopPropagation()}
                autoFocus
              />
            ) : (
              <div className="subject-name">{subject.name}</div>
            )}
          </div>
          <div className="subject-stats">
            Total Study Time: {formatDuration(subject.totalStudyTimeMin)}
          </div>
          <div className="subject-actions">
            {editingId === subject.id ? (
              <>
                <button className="btn-primary" onClick={(e) => handleSaveEdit(subject.id, e)}>
                  Save
                </button>
                <button className="btn-secondary" onClick={handleCancelEdit}>
                  Cancel
                </button>
              </>
            ) : (
              <>
                <button className="btn-edit" onClick={(e) => handleEdit(subject, e)}>
                  Edit
                </button>
                <button className="btn-danger" onClick={(e) => handleDelete(subject.id, e)}>
                  Delete
                </button>
              </>
            )}
          </div>
        </div>
      ))}
    </div>
  )
}

export default SubjectList
