import { BrowserRouter, Routes, Route } from "react-router-dom";

import UserAuthPage from "./pages/auth/UserAuthPage";
import AdminLogin from "./pages/auth/AdminLogin";
import AdminDashboard from "./pages/admin/AdminDashboard";
import UserDashboard from "./pages/user/UserDashboard";
import PendingApproval from "./pages/auth/PendingApproval";  // 👈 new

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<UserAuthPage />} />
        <Route path="/admin-login" element={<AdminLogin />} />
        <Route path="/admin" element={<AdminDashboard />} />
        <Route path="/user" element={<UserDashboard />} />
        <Route path="/pending" element={<PendingApproval />} />  {/* 👈 new */}
      </Routes>
    </BrowserRouter>
  );
}

export default App;