import { useState, useEffect } from "react"
import { createStudySession } from "../services/api"

function StudySessionForm({ subjectId, onSessionCreated, onCancel }) {
  const [duration, setDuration] = useState("")
  const [notes, setNotes] = useState("")
  const [date, setDate] = useState("")
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(false)

  // Set default date to current local time
  useEffect(() => {
    const now = new Date()
    const offset = now.getTimezoneOffset() * 60000
    const localISOTime = new Date(now - offset).toISOString().slice(0, 16)
    setDate(localISOTime)
  }, [])

 const handleSubmit = async (e) => {
  e.preventDefault()

  if (!duration || !date || !subjectId) {
    setError("Subject, duration, and date are required.")
    return
  }

  try {
    setLoading(true)
    setError(null)

    await createStudySession({
      subjectId: subjectId,   
      durationMin: parseFloat(duration),
      notes: notes || "",
      timestamp: new Date(date).toISOString()
    })

    onSessionCreated()
  } catch (err) {
    setError("Failed to create study session.")
  } finally {
    setLoading(false)
  }
}

  return (
    <form className="session-form" onSubmit={handleSubmit}>
      {error && <div className="error-banner">{error}</div>}

      <div className="form-group">
        <label>Duration (minutes)</label>
        <input
          type="number"
          min="1"
          step="0.1"
          value={duration}
          onChange={(e) => setDuration(e.target.value)}
          required
        />
      </div>

      <div className="form-group">
        <label>Date & Time</label>
        <input
          type="datetime-local"
          value={date}
          onChange={(e) => setDate(e.target.value)}
          required
        />
      </div>

      <div className="form-group">
        <label>Notes (optional)</label>
        <textarea
          value={notes}
          onChange={(e) => setNotes(e.target.value)}
          rows="3"
        />
      </div>

      <div className="form-actions">
        <button type="submit" className="btn-primary" disabled={loading}>
          {loading ? "Saving..." : "Save Session"}
        </button>
        <button type="button" onClick={onCancel}>
          Cancel
        </button>
      </div>
    </form>
  )
}

export default StudySessionForm