import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../styles/user.css";

// 🧠 MOCK USER DATA — replace with axios.get("/user/profile") later
const MOCK_USER = {
  name: "Rahul Traveller",
  email: "user@test.com",
  company: "TravelCo",
  address: "123 MG Road, Kolkata, India",
};

// 🧠 MOCK HOTEL DATA — replace with axios.get("/hotels") later
const MOCK_HOTELS = [
  { id: 1, name: "The Grand Kolkata",   location: "Kolkata",  rating: 4.8, image: "🏨" },
  { id: 2, name: "Delhi Palace Hotel",  location: "Delhi",    rating: 4.5, image: "🏩" },
  { id: 3, name: "Mumbai Sea View",     location: "Mumbai",   rating: 4.7, image: "🏪" },
  { id: 4, name: "Bangalore Tech Stay", location: "Bangalore",rating: 4.3, image: "🏬" },
  { id: 5, name: "Jaipur Royal Inn",    location: "Jaipur",   rating: 4.6, image: "🏰" },
  { id: 6, name: "Goa Beach Resort",    location: "Goa",      rating: 4.9, image: "🌴" },
];

function UserDashboard() {
  const navigate = useNavigate();

  // 🧠 controls which sidebar tab is active
  const [activeTab, setActiveTab] = useState("profile");

  // 🧠 search and filter state for hotels tab
  const [searchText, setSearchText] = useState("");
  const [selectedLocation, setSelectedLocation] = useState("All");

  function handleLogout() {
    navigate("/");
  }

  // 🧠 get unique locations from hotel list for the filter dropdown
  function getLocations() {
    const locations = MOCK_HOTELS.map(function(hotel) {
      return hotel.location;
    });
    // 🧠 Set removes duplicates, Array.from converts it back to array
    return ["All", ...Array.from(new Set(locations))];
  }

  // 🧠 filter hotels based on search text AND selected location
  function getFilteredHotels() {
    return MOCK_HOTELS.filter(function(hotel) {
      const matchesSearch = hotel.name.toLowerCase().includes(searchText.toLowerCase());
      const matchesLocation = selectedLocation === "All" || hotel.location === selectedLocation;
      return matchesSearch && matchesLocation;
      // 🧠 both must be true for hotel to show
    });
  }

  const filteredHotels = getFilteredHotels();

  return (
    <div className="user-page">

      {/* ── SIDEBAR ── */}
      <div className="sidebar">
        <div className="sidebar-logo">⚡ ERevMax</div>

        <nav className="sidebar-nav">
          <button
            className={activeTab === "profile" ? "nav-btn active" : "nav-btn"}
            onClick={function() { setActiveTab("profile"); }}
          >
            👤 My Profile
          </button>

          <button
            className={activeTab === "hotels" ? "nav-btn active" : "nav-btn"}
            onClick={function() { setActiveTab("hotels"); }}
          >
            🏨 Hotels
          </button>
        </nav>

        <button className="logout-btn" onClick={handleLogout}>
          🚪 Logout
        </button>
      </div>

      {/* ── MAIN CONTENT ── */}
      <div className="main-content">

        {/* ── PROFILE TAB ── */}
        {activeTab === "profile" && (
          <div>
            <div className="top-bar">
              <h1>My Profile 👤</h1>
              <span className="user-badge">Travel Agent</span>
            </div>

            {/* STATS ROW */}
            <div className="stats-row">
              <div className="stat-card">
                <span className="stat-number">{MOCK_HOTELS.length}</span>
                <span className="stat-label">Hotels Available</span>
              </div>
              <div className="stat-card approved">
                <span className="stat-number">
                  {/* 🧠 add all ratings then divide by count to get average */}
                  {(MOCK_HOTELS.reduce(function(sum, h) {
                    return sum + h.rating;
                  }, 0) / MOCK_HOTELS.length).toFixed(1)}
                </span>
                <span className="stat-label">Avg Hotel Rating</span>
              </div>
              <div className="stat-card pending">
                <span className="stat-number">
                  {/* 🧠 reusing our getLocations() but minus "All" */}
                  {getLocations().length - 1}
                </span>
                <span className="stat-label">Locations Available</span>
              </div>
            </div>

            {/* PROFILE CARD */}
            <div className="profile-card">
              <div className="profile-avatar">👤</div>

              <div className="profile-info">
                <div className="info-row">
                  <span className="info-label">Full Name</span>
                  <span className="info-value">{MOCK_USER.name}</span>
                </div>
                <div className="info-row">
                  <span className="info-label">Email</span>
                  <span className="info-value">{MOCK_USER.email}</span>
                </div>
                <div className="info-row">
                  <span className="info-label">Company</span>
                  <span className="info-value">{MOCK_USER.company}</span>
                </div>
                <div className="info-row">
                  <span className="info-label">Address</span>
                  <span className="info-value">{MOCK_USER.address}</span>
                </div>
              </div>
            </div>

            {/* TOP RATED HOTELS PREVIEW */}
            <div className="section-header">
              <h2>🏆 Top Rated Hotels</h2>
              <button
                type="button"
                className="view-all-btn"
                onClick={function() { setActiveTab("hotels"); }}
              >
                View All →
              </button>
            </div>

            <div className="hotel-grid">
              {/* 🧠 sort by rating descending, take top 3 */}
              {MOCK_HOTELS
                .slice()
                .sort(function(a, b) { return b.rating - a.rating; })
                .slice(0, 3)
                .map(function(hotel) {
                  return (
                    <div className="hotel-card" key={hotel.id}>
                      <div className="hotel-emoji">{hotel.image}</div>
                      <div className="hotel-info">
                        <h3>{hotel.name}</h3>
                        <p>📍 {hotel.location}</p>
                        <p>⭐ {hotel.rating} / 5</p>
                      </div>
                    </div>
                  );
                })}
            </div>

          </div>
        )}

        {/* ── HOTELS TAB ── */}
        {activeTab === "hotels" && (
          <div>
            <div className="top-bar">
              <h1>Hotels 🏨</h1>
              <span className="user-badge">Travel Agent</span>
            </div>

            {/* SEARCH + FILTER ROW */}
            <div className="search-row">
              <input
                type="text"
                className="search-input"
                placeholder="🔍 Search hotels..."
                value={searchText}
                onChange={function(e) { setSearchText(e.target.value); }}
              />

              <select
                className="location-select"
                value={selectedLocation}
                onChange={function(e) { setSelectedLocation(e.target.value); }}
              >
                {getLocations().map(function(loc) {
                  return (
                    <option key={loc} value={loc}>{loc}</option>
                  );
                })}
              </select>
            </div>

            {/* HOTEL CARDS */}
            <div className="hotel-grid">
              {filteredHotels.length === 0 && (
                <p className="no-results">No hotels found. Try a different search!</p>
              )}

              {filteredHotels.map(function(hotel) {
                return (
                  <div className="hotel-card" key={hotel.id}>
                    <div className="hotel-emoji">{hotel.image}</div>
                    <div className="hotel-info">
                      <h3>{hotel.name}</h3>
                      <p>📍 {hotel.location}</p>
                      <p>⭐ {hotel.rating} / 5</p>
                    </div>
                  </div>
                );
              })}
            </div>

            {/* MAP PLACEHOLDER — replace with real Google Map later */}
            <div className="map-placeholder">
              🗺️ Google Map will load here
              <span>( API key needed )</span>
            </div>

          </div>
        )}

      </div>
    </div>
  );
}

export default UserDashboard;