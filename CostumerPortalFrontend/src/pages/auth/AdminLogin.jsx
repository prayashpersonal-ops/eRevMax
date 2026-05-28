import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../../styles/login.css";

function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  function handleEmailChange(event) {
    setEmail(event.target.value);
  }

  function handlePasswordChange(event) {
    setPassword(event.target.value);
  }

  async function handleLogin(e) {
    e.preventDefault();
    setLoading(true);
    setErrorMessage("");

    try {
        console.log("Sending:", { email, password, role: "ADMIN" });
      const response = await axios.post("http://localhost:8080/admin/login", {
        email: email,
        password: password,
        // role: "ADMIN",
      });

      console.log("Login success:", response.data);
      navigate("/admin");

    } catch (error) {
      if (error.response) {
        setErrorMessage("Invalid email or password");
      } else {
        setErrorMessage("Could not connect to server. Please try again.");
      }
    }

    setLoading(false);
  }

  return (
    <div className="login-page">
      {/* LEFT SIDE */}
      <div className="left-section">
        <div className="logo-box"></div>

        <div className="hero-content">
          <h1>
            Welcome Back, <br />
            <span>Admin</span>
          </h1>

          <div className="underline"></div>

          <p>
            Access your dashboard, manage customers, hotels and analytics with ease.
          </p>
        </div>

        <div className="feature-list">
          <div className="feature-card">
            <div className="feature-icon">🔒</div>
            <div>
              <h3>Secure Access</h3>
              <p>Enterprise-grade authentication</p>
            </div>
          </div>

          <div className="feature-card">
            <div className="feature-icon">📈</div>
            <div>
              <h3>Real-time Analytics</h3>
              <p>Track customer insights instantly</p>
            </div>
          </div>

          <div className="feature-card">
            <div className="feature-icon">👥</div>
            <div>
              <h3>User Management</h3>
              <p>Manage roles and permissions</p>
            </div>
          </div>
        </div>
      </div>

      {/* RIGHT SIDE */}
      <div className="right-section">
        <div className="login-card">
          <div className="lock-circle">🔐</div>

          <h1>Admin Login</h1>

          <p className="subtitle">
            Enter your credentials to continue
          </p>

          <input
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={handleEmailChange}
          />

          <input
            type="password"
            placeholder="Enter your password"
            value={password}
            onChange={handlePasswordChange}
          />

          <div className="login-options">
            <label>
              <input type="checkbox" />
              Remember me
            </label>

            <span>Forgot password?</span>
          </div>

          {errorMessage && (
            <p className="error-message">{errorMessage}</p>
          )}

          <button
            type="button"
            onClick={handleLogin}
            disabled={loading}
          >
            {loading ? "Logging in..." : "Login →"}
          </button>
        </div>
      </div>
    </div>
  );
}

export default Login;