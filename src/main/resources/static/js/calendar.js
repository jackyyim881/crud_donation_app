console.log("User ID:", userId);

document.addEventListener("DOMContentLoaded", function () {
  const calendarEl = document.getElementById("calendar");
  const userId = calendarEl.dataset.userId;

  const calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: "dayGridMonth",
    headerToolbar: {
      left: "prev,next today",
      center: "title",
      right: "dayGridMonth,timeGridWeek,timeGridDay",
    },
    selectable: true,
    editable: true,
    events: function (fetchInfo, successCallback, failureCallback) {
      axios
        .get(`/api/v1/events/user/${userId}`)
        .then((response) => {
          const events = response.data.map((event) => ({
            id: event.id,
            title: event.title,
            start: event.start,
            end: event.end,
            color: event.color,
            description: event.description,
          }));
          successCallback(events);
        })
        .catch((error) => {
          console.error("Error fetching events:", error);
          failureCallback(error);
        });
    },
    dateClick: function (info) {
      // Set the default date for the new event in the form
      document.getElementById("eventStart").value = info.dateStr;
      document.getElementById("eventEnd").value = info.dateStr;
      document.getElementById("scheduleModal").classList.remove("hidden");
    },
    eventClick: function (info) {
      // Fill the form with event data for editing
      const event = info.event;
      document.getElementById("eventTitle").value = event.title;
      document.getElementById("eventDescription").value =
        event.extendedProps.description;
      document.getElementById("eventStart").value = event.start
        .toISOString()
        .slice(0, 16);
      document.getElementById("eventEnd").value = event.end
        ? event.end.toISOString().slice(0, 16)
        : event.start.toISOString().slice(0, 16);
      document.getElementById("eventCategory").value =
        event.extendedProps.category;
      document.getElementById("eventColor").value = event.backgroundColor;
      document.getElementById("scheduleModal").classList.remove("hidden");
      document.getElementById("scheduleForm").dataset.eventId = event.id;
    },
    eventDrop: function (info) {
      // Update event on drop
      const updatedEvent = {
        id: info.event.id,
        title: info.event.title,
        start: info.event.start.toISOString(),
        end: info.event.end ? info.event.end.toISOString() : null,
        color: info.event.backgroundColor,
      };
      axios
        .put(`/api/v1/events/${updatedEvent.id}`, updatedEvent)
        .then((response) => {
          console.log("Event updated successfully:", response.data);
        })
        .catch((error) => {
          console.error("Error updating event:", error);
        });
    },
    eventResize: function (info) {
      // Update event on resize
      const updatedEvent = {
        id: info.event.id,
        title: info.event.title,
        start: info.event.start.toISOString(),
        end: info.event.end ? info.event.end.toISOString() : null,
        color: info.event.backgroundColor,
      };
      axios
        .put(`/api/v1/events/${updatedEvent.id}`, updatedEvent)
        .then((response) => {
          console.log("Event updated successfully:", response.data);
        })
        .catch((error) => {
          console.error("Error updating event:", error);
        });
    },
  });

  calendar.render();

  // Handle form submission for creating or updating events
  document
    .getElementById("scheduleForm")
    .addEventListener("submit", function (e) {
      e.preventDefault();

      const form = e.target;
      const eventId = form.dataset.eventId;
      const newEvent = {
        title: form.title.value,
        description: form.description.value,
        start: form.start.value,
        end: form.end.value,
        category: form.category.value,
        color: form.color.value,
        userId: userId,
      };

      if (eventId) {
        // Update existing event
        axios
          .put(`/api/v1/events/${eventId}`, newEvent)
          .then((response) => {
            console.log("Event updated successfully:", response.data);
            calendar.refetchEvents();
            document.getElementById("scheduleModal").classList.add("hidden");
          })
          .catch((error) => {
            console.error("Error updating event:", error);
          });
      } else {
        // Create new event
        axios
          .post("/api/v1/events", newEvent)
          .then((response) => {
            console.log("Event created successfully:", response.data);
            calendar.refetchEvents();
            document.getElementById("scheduleModal").classList.add("hidden");
          })
          .catch((error) => {
            console.error("Error creating event:", error);
          });
      }
    });

  // Handle event deletion
  document
    .getElementById("deleteEventBtn")
    .addEventListener("click", function () {
      const eventId = document.getElementById("scheduleForm").dataset.eventId;
      if (eventId) {
        axios
          .delete(`/api/v1/events/${eventId}`)
          .then((response) => {
            console.log("Event deleted successfully:", response.data);
            calendar.refetchEvents();
            document.getElementById("scheduleModal").classList.add("hidden");
          })
          .catch((error) => {
            console.error("Error deleting event:", error);
          });
      }
    });

  // Handle cancel button click
  document
    .getElementById("cancelScheduleBtn")
    .addEventListener("click", function () {
      document.getElementById("scheduleModal").classList.add("hidden");
    });
});
