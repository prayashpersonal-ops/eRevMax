
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../styles/login.css";

function UserAuthPage() {
  const navigate = useNavigate();

  // 🧠 this controls which tab is active: "login" or "signup"
  const [activeTab, setActiveTab] = useState("login");

  // LOGIN fields
  const [loginEmail, setLoginEmail] = useState("");
  const [loginPassword, setLoginPassword] = useState("");
  const [loginError, setLoginError] = useState("");
  const [loginLoading, setLoginLoading] = useState(false);

  // SIGNUP fields
  const [signupName, setSignupName] = useState("");
  const [signupEmail, setSignupEmail] = useState("");
  const [signupPassword, setSignupPassword] = useState("");
  const [signupCompany, setSignupCompany] = useState("");
  const [signupAddress, setSignupAddress] = useState("");
  const [signupError, setSignupError] = useState("");
  const [signupLoading, setSignupLoading] = useState(false);

  // ── LOGIN HANDLER ──
  function handleLogin(e) {
    e.preventDefault();
    setLoginLoading(true);
    setLoginError("");

    // 🧠 MOCK — replace with axios.post("/user/login") later
    if (loginEmail === "user@test.com" && loginPassword === "1234") {
      navigate("/user");
    } else {
      setLoginError("Invalid email or password");
    }

    setLoginLoading(false);
  }

  // ── SIGNUP HANDLER ──
  function handleSignup(e) {
    e.preventDefault();
    setSignupLoading(true);
    setSignupError("");

    // 🧠 Basic validation — check nothing is empty
    if (!signupName || !signupEmail || !signupPassword || !signupCompany || !signupAddress) {
      setSignupError("Please fill in all fields");
      setSignupLoading(false);
      return; // 🧠 stop here, don't continue
    }

    // 🧠 MOCK — replace with axios.post("/user/signup") later
    // For now just navigate to pending page
    navigate("/pending");

    setSignupLoading(false);
  }

  return (
    <div className="login-page">

      {/* ── LEFT SIDE ── */}
      <div className="left-section">
        <div className="logo-box"></div>

        <div className="hero-content">
          <h1>
            Your Travel <br />
            <span>Portal</span>
          </h1>
          <div className="underline"></div>
          <p>
            Find top rated hotels, manage bookings and grow your travel business with ease.
          </p>
        </div>

        <div className="feature-list">
          <div className="feature-card">
            <div className="feature-icon">🏨</div>
            <div>
              <h3>Hotel Discovery</h3>
              <p>Browse top rated hotels by location</p>
            </div>
          </div>

          <div className="feature-card">
            <div className="feature-icon">🗺️</div>
            <div>
              <h3>Maps Integration</h3>
              <p>Find hotels near your destinations</p>
            </div>
          </div>

          <div className="feature-card">
            <div className="feature-icon">📊</div>
            <div>
              <h3>Business Analytics</h3>
              <p>Track your bookings and performance</p>
            </div>
          </div>
        </div>

        {/* 🧠 Admin corner link — sits at bottom of left section */}
        <div className="admin-corner">
          <span>Are you an admin?</span>
          <button
            type="button"
            className="admin-corner-btn"
            onClick={function() { navigate("/admin-login"); }}
          >
            Login here →
          </button>
        </div>

      </div>

      {/* ── RIGHT SIDE ── */}
      <div className="right-section">
        <div className="login-card">

          <div className="lock-circle">
            {activeTab === "login" ? "👤" : "✍️"}
          </div>

          {/* ── TABS ── */}
          <div className="tab-row">
            <button
              type="button"
              className={activeTab === "login" ? "tab-btn active" : "tab-btn"}
              onClick={function() {
                setActiveTab("login");
                setLoginError("");
                setSignupError("");
              }}
            >
              Login
            </button>
            <button
              type="button"
              className={activeTab === "signup" ? "tab-btn active" : "tab-btn"}
              onClick={function() {
                setActiveTab("signup");
                setLoginError("");
                setSignupError("");
              }}
            >
              Sign Up
            </button>
          </div>

          {/* ── LOGIN FORM ── */}
          {activeTab === "login" && (
            <div>
              <p className="subtitle">Welcome back! Login to continue</p>

              <input
                type="email"
                placeholder="Enter your email"
                value={loginEmail}
                onChange={function(e) { setLoginEmail(e.target.value); }}
              />

              <input
                type="password"
                placeholder="Enter your password"
                value={loginPassword}
                onChange={function(e) { setLoginPassword(e.target.value); }}
              />

              <div className="login-options">
                <label>
                  <input type="checkbox" /> Remember me
                </label>
                <span>Forgot password?</span>
              </div>

              {loginError && <p className="error-message">{loginError}</p>}

              <button
                type="button"
                onClick={handleLogin}
                disabled={loginLoading}
              >
                {loginLoading ? "Logging in..." : "Login →"}
              </button>
            </div>
          )}

          {/* ── SIGNUP FORM ── */}
          {activeTab === "signup" && (
            <div>
              <p className="subtitle">Create your account to get started</p>

              <input
                type="text"
                placeholder="Full name"
                value={signupName}
                onChange={function(e) { setSignupName(e.target.value); }}
              />

              <input
                type="email"
                placeholder="Email address"
                value={signupEmail}
                onChange={function(e) { setSignupEmail(e.target.value); }}
              />

              <input
                type="password"
                placeholder="Create a password"
                value={signupPassword}
                onChange={function(e) { setSignupPassword(e.target.value); }}
              />

              <input
                type="text"
                placeholder="Company name"
                value={signupCompany}
                onChange={function(e) { setSignupCompany(e.target.value); }}
              />

              <input
                type="text"
                placeholder="Address"
                value={signupAddress}
                onChange={function(e) { setSignupAddress(e.target.value); }}
              />

              {signupError && <p className="error-message">{signupError}</p>}

              <button
                type="button"
                onClick={handleSignup}
                disabled={signupLoading}
              >
                {signupLoading ? "Signing up..." : "Create Account →"}
              </button>
            </div>
          )}

        </div>
      </div>

    </div>
  );
}

export default UserAuthPage;