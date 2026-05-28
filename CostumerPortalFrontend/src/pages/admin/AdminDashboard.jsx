import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../styles/admin.css";

// 🧠 MOCK DATA — replace with axios.get("/admin/users") later
const MOCK_USERS = [
  { id: 1, username: "rahul_t", email: "rahul@travel.com", company: "TravelCo",   status: "approved" },
  { id: 2, username: "priya_s", email: "priya@hotels.com", company: "HotelWorld", status: "pending"  },
  { id: 3, username: "amit_k",  email: "amit@trips.com",   company: "TripMakers", status: "pending"  },
  { id: 4, username: "sneha_r", email: "sneha@voyage.com", company: "VoyageCo",   status: "approved" },
  { id: 5, username: "james_w", email: "james@roam.com",   company: "RoamFree",   status: "denied"   },
];

function AdminDashboard() {
  const navigate = useNavigate();

  const [users, setUsers] = useState(MOCK_USERS);
  const [activeTab, setActiveTab] = useState("dashboard");

  // 🧠 Count users by status
  const totalUsers    = users.length;
  
  const pendingUsers  = users.filter(function(u) { return u.status === "pending";  }).length;
  const approvedUsers = users.filter(function(u) { return u.status === "approved"; }).length;

  // ✅ Approve a user
  function handleApprove(id) {
    const updated = users.map(function(u) {
      if (u.id === id) {
        return { ...u, status: "approved" };
        // 🧠 ...u means "copy all fields of u, then override status"
      }
      return u;
    });
    setUsers(updated);
  }

  // ❌ Deny a user
  function handleDeny(id) {
    const updated = users.map(function(u) {
      if (u.id === id) {
        return { ...u, status: "denied" };
      }
      return u;
    });
    setUsers(updated);
  }

  // 🚪 Logout
  function handleLogout() {
    navigate("/");
  }

  // 🧠 Only pending users for the approvals section
  const pendingList = users.filter(function(u) { return u.status === "pending"; });

  return (
    <div className="admin-page">

      {/* ── SIDEBAR ── */}
      <div className="sidebar">
        <div className="sidebar-logo">⚡ ERevMax</div>

        <nav className="sidebar-nav">
          <button
            className={activeTab === "dashboard" ? "nav-btn active" : "nav-btn"}
            onClick={function() { setActiveTab("dashboard"); }}
          >
            📊 Dashboard
          </button>

          <button
            className={activeTab === "users" ? "nav-btn active" : "nav-btn"}
            onClick={function() { setActiveTab("users"); }}
          >
            👥 All Users
          </button>
        </nav>

        <button className="logout-btn" onClick={handleLogout}>
          🚪 Logout
        </button>
      </div>

      {/* ── MAIN CONTENT ── */}
      <div className="main-content">

        <div className="top-bar">
          <h1>Welcome, Admin 👋</h1>
          <span className="admin-badge">Administrator</span>
        </div>

        {/* STATS ROW */}
        <div className="stats-row">
          <div className="stat-card">
            <span className="stat-number">{totalUsers}</span>
            <span className="stat-label">Total Users</span>
          </div>
          <div className="stat-card pending">
            <span className="stat-number">{pendingUsers}</span>
            <span className="stat-label">Pending Approval</span>
          </div>
          <div className="stat-card approved">
            <span className="stat-number">{approvedUsers}</span>
            <span className="stat-label">Approved Users</span>
          </div>
        </div>

        {/* PENDING APPROVALS TABLE */}
        {pendingList.length > 0 && (
          <div className="section">
            <h2>🔔 Pending Approvals</h2>
            <table className="user-table">
              <thead>
                <tr>
                  <th>Username</th>
                  <th>Email</th>
                  <th>Company</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {pendingList.map(function(user) {
                  return (
                    <tr key={user.id}>
                      <td>{user.username}</td>
                      <td>{user.email}</td>
                      <td>{user.company}</td>
                      <td>
                        <button className="approve-btn" onClick={function() { handleApprove(user.id); }}>✅ Approve</button>
                        <button className="deny-btn"    onClick={function() { handleDeny(user.id); }}>❌ Deny</button>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        )}

        {/* ALL USERS TABLE */}
        <div className="section">
          <h2>👥 All Users</h2>
          <table className="user-table">
            <thead>
              <tr>
                <th>Username</th>
                <th>Email</th>
                <th>Company</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {users.map(function(user) {
                return (
                  <tr key={user.id}>
                    <td>{user.username}</td>
                    <td>{user.email}</td>
                    <td>{user.company}</td>
                    <td>
                      <span className={"status-badge " + user.status}>
                        {user.status}
                      </span>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>

      </div>
    </div>
  );
}

export default AdminDashboard;