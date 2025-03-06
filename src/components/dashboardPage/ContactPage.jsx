import { useState, useEffect } from "react";
import axiosInstance from "../axiosInstance";

const ContactPage = () => {
  const [contacts, setContacts] = useState([]);
  const [selectedContact, setSelectedContact] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [newContact, setNewContact] = useState({ name: "", socialMedia: "" });
  const [newOrder, setNewOrder] = useState({ platform: "" });
  const [isAddingOrder, setIsAddingOrder] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");

  const socialMediaPlatforms = [
    { value: "", label: "Pilih Media Sosial" },
    { value: "facebook", label: "Facebook" },
    { value: "instagram", label: "Instagram" },
    { value: "twitter", label: "Twitter" },
    { value: "linkedin", label: "LinkedIn" },
  ];

  const OrderPlatforms = [
    { value: "", label: "Pilih Platform" },
    { value: "tokopedia", label: "Tokopedia" },
    { value: "shopee", label: "Shopee" },
    { value: "grabfood", label: "GrabFood" },
    { value: "gofood", label: "GoFood" },
  ];

  useEffect(() => {
    axiosInstance
      .get("/contacts")
      .then((response) => {
        setContacts(response.data);
      })
      .catch((error) => {
        console.error("Gagal mengambil kontak:", error);
        alert("Gagal mengambil data kontak. Silakan coba lagi nanti.");
      });
  }, []);

  const handleRead = (contact) => {
    setSelectedContact(contact);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setSelectedContact(null);
    setIsModalOpen(false);
  };

  const closeAddModal = () => {
    setIsAddModalOpen(false);
    setNewContact({ name: "", socialMedia: "" });
    setNewOrder({ name: "", platform: "" });
  };

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm(
      "Apakah Anda yakin ingin menghapus kontak ini?"
    );
    if (!confirmDelete) return;

    try {
      await axiosInstance.delete(`/contacts/${id}`);
      alert("Kontak berhasil dihapus!");
      setContacts((prevContacts) =>
        prevContacts.filter((contact) => contact._id !== id)
      );
    } catch (error) {
      console.error("Gagal menghapus kontak:", error);
      alert("Terjadi kesalahan saat menghapus kontak.");
    }
  };

  const handleAddContactOrOrder = async (e) => {
    e.preventDefault();

    // Validasi untuk kontak dan order
    if (isAddingOrder) {
      if (!newContact.name || !newOrder.platform) {
        alert("Semua data harus diisi!");
        return;
      }

      const orderData = {
        name: newContact.name,
        socialMedia: newContact.socialMedia,
        platform: newOrder.platform,
      };

      try {
        const response = await axiosInstance.post("/contacts", orderData);
        alert("Kontak dan order berhasil ditambahkan!");
        setContacts((prevContacts) => [...prevContacts, response.data]);
        setNewContact({ name: "", socialMedia: "" });
        setNewOrder({ platform: "" });
        closeAddModal();
      } catch (error) {
        console.error("Gagal menambahkan kontak dan order:", error);
        alert("Terjadi kesalahan saat menambahkan kontak dan order.");
      }
    } else {
      if (!newContact.name || !newContact.socialMedia) {
        alert("Semua data kontak harus diisi!");
        return;
      }

      try {
        const response = await axiosInstance.post("/contacts", newContact);
        alert("Kontak berhasil ditambahkan!");
        setContacts((prevContacts) => [...prevContacts, response.data]);
        setNewContact({ name: "", socialMedia: "" });
        closeAddModal();
      } catch (error) {
        console.error("Gagal menambahkan kontak:", error);
        alert("Terjadi kesalahan saat menambahkan kontak.");
      }
    }
  };

  const handleContactClick = (contact) => {
    setSelectedContact(contact); // Set the selected contact
  };

  const filteredContacts = contacts.filter(
    (contact) =>
      (contact.name &&
        contact.name.toLowerCase().includes(searchQuery.toLowerCase())) ||
      (contact.socialMedia &&
        contact.socialMedia.toLowerCase().includes(searchQuery.toLowerCase()))
  );

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Manajemen Kontak</h1>

      <div className="mb-4">
        <input
          type="text"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          placeholder="Cari kontak..."
          className="p-2 border rounded w-full"
        />
      </div>

      <button
        onClick={() => setIsAddModalOpen(true)}
        className="mb-4 px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600 transition-all"
      >
        Tambah Kontak
      </button>

      <div className="bg-white shadow rounded overflow-x-auto">
        <table className="table-auto w-full border-collapse border border-gray-300">
          <thead>
            <tr>
              <th className="border border-gray-300 px-4 py-2 bg-gray-200">
                No
              </th>
              <th className="border border-gray-300 px-4 py-2 bg-gray-200">
                Nama Profil
              </th>
              <th className="border border-gray-300 px-4 py-2 bg-gray-200">
                Media Sosial
              </th>
              <th className="border border-gray-300 px-4 py-2 bg-gray-200">
                Aksi
              </th>
            </tr>
          </thead>
          <tbody>
            {filteredContacts.length > 0 ? (
              filteredContacts.map((contact, index) => (
                <tr
                  key={contact._id}
                  onClick={() => handleContactClick(contact)}
                  className={`cursor-pointer ${
                    selectedContact?._id === contact._id ? "bg-blue-100" : ""
                  }`}
                >
                  <td className="border border-gray-300 px-4 py-2 text-center">
                    {index + 1}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    {contact.name}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    {contact.socialMedia ? contact.socialMedia : contact.platform}
                  </td>
                  <td className="border border-gray-300 px-4 py-2 text-center">
                    <div className="flex justify-center space-x-2">
                      <button
                        onClick={() => handleRead(contact)}
                        className="px-2 text-2xl text-blue-500 bg-white border-2 border-blue-500 hover:bg-blue-500 hover:text-white rounded-xl transition-all"
                      >
                        <i className="ri-eye-line"></i>
                      </button>
                      <button
                        onClick={() => handleDelete(contact._id)}
                        className="px-2 text-2xl text-red-500 bg-white border-2 border-red-500 hover:bg-red-500 hover:text-white rounded-xl transition-all"
                      >
                        <i className="ri-delete-bin-5-fill"></i>
                      </button>
                    </div>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td
                  colSpan="4"
                  className="border border-gray-300 px-4 py-2 text-center text-gray-500"
                >
                  Tidak ada kontak.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      {/* Modal for adding a new contact or order */}
      {isAddModalOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
          <div className="bg-white p-6 rounded shadow-lg w-full max-w-md">
            <h2 className="text-xl font-bold mb-4 text-center">
              {isAddingOrder
                ? "Tambah Kontak dan Order Baru"
                : "Tambah Kontak Baru"}
            </h2>
            <div className="flex justify-between mb-4">
              <button
                onClick={() => setIsAddingOrder(false)}
                className={`w-full p-2 ${
                  !isAddingOrder ? "bg-green-500" : "bg-gray-300"
                } text-white rounded hover:bg-green-600 transition-all`}
              >
                Tambah Kontak
              </button>

              <button
                onClick={() => setIsAddingOrder(true)}
                className={`w-full p-2 ${
                  isAddingOrder ? "bg-green-500" : "bg-gray-300"
                } text-white rounded hover:bg-green-600 transition-all`}
              >
                Tambah Order
              </button>
            </div>

            {isAddingOrder ? (
              <form onSubmit={handleAddContactOrOrder}>
                <div className="mb-4">
                  <label className="block mb-2 font-semibold">
                    Nama Profil
                  </label>
                  <input
                    type="text"
                    className="w-full p-2 border rounded"
                    value={newContact.name}
                    onChange={(e) =>
                      setNewContact({ ...newContact, name: e.target.value })
                    }
                    required
                  />
                </div>

                <div className="mb-4">
                  <label className="block mb-2 font-semibold">Platform</label>
                  <select
                    className="w-full p-2 border rounded"
                    value={newOrder.platform}
                    onChange={(e) =>
                      setNewOrder({ ...newOrder, platform: e.target.value })
                    }
                    required
                  >
                    {OrderPlatforms.map((platform) => (
                      <option key={platform.value} value={platform.value}>
                        {platform.label}
                      </option>
                    ))}
                  </select>
                </div>

                <div className="flex justify-center">
                  <button
                    type="submit"
                    className="w-full p-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition-all"
                  >
                    Simpan
                  </button>
                </div>
              </form>
            ) : (
              <form onSubmit={handleAddContactOrOrder}>
                <div className="mb-4">
                  <label className="block mb-2 font-semibold">
                    Nama Profil
                  </label>
                  <input
                    type="text"
                    className="w-full p-2 border rounded"
                    value={newContact.name}
                    onChange={(e) =>
                      setNewContact({ ...newContact, name: e.target.value })
                    }
                    required
                  />
                </div>

                <div className="mb-4">
                  <label className="block mb-2 font-semibold">
                    Media Sosial
                  </label>
                  <select
                    className="w-full p-2 border rounded"
                    value={newContact.socialMedia}
                    onChange={(e) =>
                      setNewContact({
                        ...newContact,
                        socialMedia: e.target.value,
                      })
                    }
                    required
                  >
                    {socialMediaPlatforms.map((platform) => (
                      <option key={platform.value} value={platform.value}>
                        {platform.label}
                      </option>
                    ))}
                  </select>
                </div>

                <div className="flex justify-center">
                  <button
                    type="submit"
                    className="w-full p-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition-all"
                  >
                    Simpan
                  </button>
                </div>
              </form>
            )}

            <button
              onClick={closeAddModal}
              className="absolute top-2 right-2 text-xl text-gray-500"
            >
              <i className="ri-close-circle-line"></i>
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default ContactPage;
