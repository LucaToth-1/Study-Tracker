import { useState } from 'react'
import { createSubject } from '../services/api'

function SubjectForm({ onSubjectCreated, onCancel }) {
  const [name, setName] = useState('')
  const [error, setError] = useState(null)
  const [isSubmitting, setIsSubmitting] = useState(false)

  const handleSubmit = async (e) => {
    e.preventDefault()
    
    if (!name.trim()) {
      setError('Subject name is required')
      return
    }

    setIsSubmitting(true)
    setError(null)

    try {
      await createSubject({ name: name.trim() })
      setName('')
      onSubjectCreated()
    } catch (err) {
      setError('Failed to create subject: ' + err.message)
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <div className="form-card">
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="subject-name">Subject Name</label>
          <input
            id="subject-name"
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder="e.g., Mathematics, Physics, Programming"
            disabled={isSubmitting}
          />
        </div>
        {error && <div className="form-error">{error}</div>}
        <div className="form-actions">
          <button type="submit" className="btn-primary" disabled={isSubmitting}>
            {isSubmitting ? 'Creating...' : 'Create Subject'}
          </button>
          <button type="button" className="btn-secondary" onClick={onCancel} disabled={isSubmitting}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  )
}

export default SubjectForm
