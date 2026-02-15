import { useState } from 'react'
import { createStudySession } from '../services/api'

function StudySessionForm({ subjectId, subjects, onSessionCreated, onCancel }) {
  const [formData, setFormData] = useState({
    subjectId: subjectId,
    durationMin: '',
    notes: ''
  })
  const [error, setError] = useState(null)
  const [isSubmitting, setIsSubmitting] = useState(false)

  const handleSubmit = async (e) => {
    e.preventDefault()
    
    if (!formData.durationMin || parseFloat(formData.durationMin) <= 0) {
      setError('Duration must be greater than 0')
      return
    }

    setIsSubmitting(true)
    setError(null)

    try {
      await createStudySession({
        subjectId: parseInt(formData.subjectId),
        durationMin: parseFloat(formData.durationMin),
        notes: formData.notes.trim()
      })
      setFormData({ subjectId: subjectId, durationMin: '', notes: '' })
      onSessionCreated()
    } catch (err) {
      setError('Failed to create study session: ' + err.message)
    } finally {
      setIsSubmitting(false)
    }
  }

  const handleChange = (e) => {
    const { name, value } = e.target
    setFormData(prev => ({
      ...prev,
      [name]: value
    }))
  }

  return (
    <div className="form-card">
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="subject">Subject</label>
          <select
            id="subject"
            name="subjectId"
            value={formData.subjectId}
            onChange={handleChange}
            disabled={isSubmitting}
          >
            {subjects.map(subject => (
              <option key={subject.id} value={subject.id}>
                {subject.name}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="duration">Duration (minutes)</label>
          <input
            id="duration"
            type="number"
            name="durationMin"
            step="0.1"
            min="0.1"
            value={formData.durationMin}
            onChange={handleChange}
            placeholder="e.g., 45, 90, 120"
            disabled={isSubmitting}
          />
        </div>

        <div className="form-group">
          <label htmlFor="notes">Notes (optional)</label>
          <textarea
            id="notes"
            name="notes"
            value={formData.notes}
            onChange={handleChange}
            placeholder="What did you study? Any key topics or concepts?"
            disabled={isSubmitting}
          />
        </div>

        {error && <div className="form-error">{error}</div>}

        <div className="form-actions">
          <button type="submit" className="btn-primary" disabled={isSubmitting}>
            {isSubmitting ? 'Creating...' : 'Create Session'}
          </button>
          <button type="button" className="btn-secondary" onClick={onCancel} disabled={isSubmitting}>
            Cancel
          </button>
        </div>
      </form>
    </div>
  )
}

export default StudySessionForm
