$(document).ready(function () {
  // Function to fetch and display donations in index.html
  function fetchDonations(filters = {}) {
    $.ajax({
      url: "/api/v1/donations",
      method: "GET",
      data: filters,
      dataType: "json",
      success: function (data) {
        const tableBody = $("#donationsTableBody");
        tableBody.empty();

        if (data.length === 0) {
          tableBody.append(`
            <tr>
              <td colspan="7" class="py-4 px-6 text-center text-gray-500">No donations found.</td>
            </tr>
          `);
          return;
        }

        // Define a threshold for the number of visible rows before blurring
        const rowThreshold = 5;

        data.forEach((donation, index) => {
          const isBlurred = index >= rowThreshold; // Blur rows beyond the threshold
          const blurClass = isBlurred ? "blur-row" : "";

          const row = `
            <tr class="hover:bg-gray-100 ${blurClass}">
              <td class="py-2 px-4 border-b">${donation.id}</td>
              <td class="py-2 px-4 border-b">$${donation.amount.toFixed(2)}</td>
              <td class="py-2 px-4 border-b">${donation.donorName}</td>
              <td class="py-2 px-4 border-b">${new Date(
                donation.donationDate
              ).toLocaleDateString()}</td>
              <td class="py-2 px-4 border-b">${
                donation.campaignName || "N/A"
              }</td>
              <td class="py-2 px-4 border-b">${
                donation.paymentMethodName || "N/A"
              }</td>
            </tr>
          `;
          tableBody.append(row);
        });

        // Add a "Show More" button if there are more than rowThreshold donations
        if (data.length > rowThreshold) {
          tableBody.append(`
            <tr id="showMoreRow">
              <td colspan="7" class="text-center py-4">
                <button id="showMoreBtn" class="bg-blue-500 text-white px-4 py-2 rounded">Show More</button>
              </td>
            </tr>
          `);

          // Add click handler to reveal blurred rows when "Show More" is clicked
          $("#showMoreBtn").click(function () {
            $(".blur-row").removeClass("blur-row");
            $("#showMoreRow").remove(); // Remove the "Show More" button
          });
        }
      },
      error: function (err) {
        alert("Error fetching donations.");
        console.error(err);
      },
    });
  }

  // Initial fetch on index.html
  if ($("#donationsTableBody").length) {
    fetchDonations();
  }

  // ... rest of your script remains unchanged
});