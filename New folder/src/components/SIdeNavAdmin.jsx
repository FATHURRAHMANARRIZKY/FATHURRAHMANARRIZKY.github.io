import PropTypes from "prop-types";
import { useState } from "react";
import { useNavigate } from "react-router-dom"; // Import useNavigate
import axiosInstance from "./axiosInstance";

const SideNavbar = ({ onMenuSelect }) => {
  const [activeMenu, setActiveMenu] = useState("Contact");
  const navigate = useNavigate(); // Gunakan navigate untuk redirect

  // Fungsi menangani klik menu
  const handleMenuClick = (menu) => {
    setActiveMenu(menu);
    onMenuSelect(menu);
  };

  // Fungsi logout dengan konfirmasi
  const handleLogout = async () => {
    const confirmLogout = window.confirm("Apakah Anda yakin ingin logout?");
    if (confirmLogout) {
      try {
        await axiosInstance.post("/logout"); // Panggil API logout
  
        // Hapus cookie JWT
        document.cookie = "token=; path=/; expires=Thu, 01 Jan 1970 00:00:00 UTC;";
  
        navigate("/login"); // Redirect ke halaman login
      } catch (error) {
        console.error("Logout gagal", error);
      }
    }
  };
  

  return (
    <div className="w-1/5 bg-gray-100 p-4 flex flex-col justify-between">
      <div>
        <h2 className="text-lg font-bold mb-4">Admin Menu</h2>
        <ul className="space-y-2">
          {["Contact", "User", "Product", "Reviews"].map((menu) => (
            <li
              key={menu}
              className={`p-2 rounded cursor-pointer ${
                activeMenu === menu ? "bg-yellow-500 text-white" : "hover:bg-gray-200 transition-all"
              }`}
              onClick={() => handleMenuClick(menu)}
            >
              {menu}
            </li>
          ))}
        </ul>
      </div>
      <button
        onClick={handleLogout}
        className="p-2 bg-white text-red-500 border-2 border-red-500 hover:text-white hover:bg-red-500 transition-all rounded-xl"
      >
        Log Out
      </button>
    </div>
  );
};

// Validasi props menggunakan PropTypes
SideNavbar.propTypes = {
  onMenuSelect: PropTypes.func.isRequired,
};

export default SideNavbar;
