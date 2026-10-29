
const API_URL = "/api/applications";

const form = document.getElementById("applicationForm");
const message = document.getElementById("message");

function escapeHTML(value) {
    return String(value ?? "").replace(/[&<>"']/g, character => ({
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#39;"
    })[character]);
}

async function requestJSON(url, options = {}) {
    const response = await fetch(url, options);
    const text = await response.text();

    if (!response.ok) {
        throw new Error(text || `Request failed: ${response.status}`);
    }

    return text ? JSON.parse(text) : null;
}

function showMessage(text, success) {
    message.textContent = text;
    message.className = success ? "success" : "error";
}

async function loadApplications() {
    try {
        const applications = await requestJSON(API_URL);

        document.getElementById("totalApplications").textContent =
            applications.length;

        const tbody = document.getElementById("applicationsTable");

        if (applications.length === 0) {
            tbody.innerHTML =
                '<tr><td colspan="4">No applications found.</td></tr>';
            return;
        }

        tbody.innerHTML = applications.map(app => `
            <tr>
                <td>${escapeHTML(app.id)}</td>
                <td>${escapeHTML(app.name)}</td>
                <td>${escapeHTML(app.email)}</td>
                <td>${escapeHTML(app.phone)}</td>
            </tr>
        `).join("");
    } catch (error) {
        showMessage("Could not load applications: " + error.message, false);
    }
}

function formatDateTime(dateTime) {
    if (!dateTime) {
        return "N/A";
    }

    const date = new Date(dateTime);

    if (isNaN(date.getTime())) {
        return dateTime;
    }

    return date.toLocaleString("en-IN", {
        day: "2-digit",
        month: "short",
        year: "numeric",
        hour: "2-digit",
        minute: "2-digit",
        hour12: true
    });
}

async function loadDuplicates() {
    try {
        const reports = await requestJSON(API_URL + "/duplicates");

        document.getElementById("totalDuplicates").textContent =
            reports.length;

        const tbody = document.getElementById("duplicatesTable");

        if (reports.length === 0) {
            tbody.innerHTML =
                '<tr><td colspan="6">No duplicate reports found.</td></tr>';
            return;
        }

        tbody.innerHTML = reports.map(report => `
            <tr>
                <td>${escapeHTML(report.id)}</td>
                <td>${escapeHTML(report.existingApplicationId)}</td>
                <td>${escapeHTML(report.submittedName)}</td>
                <td>${escapeHTML(report.matchReason)}</td>
				<td>${report.similarityScore == null
				    ? "N/A"
				    : Number(report.similarityScore).toFixed(2) + "%"}</td>

				<td>${escapeHTML(formatDateTime(report.detectedAt))}</td>
            </tr>
        `).join("");
    } catch (error) {
        showMessage("Could not load duplicate reports: " +
            error.message, false);
    }
}

form.addEventListener("submit", async event => {
    event.preventDefault();

    const application = {
        name: document.getElementById("name").value.trim(),
        email: document.getElementById("email").value.trim(),
        phone: document.getElementById("phone").value.trim()
    };

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(application)
        });

        const result = await response.text();

        if (response.ok) {
            showMessage(result || "Application registered successfully!",
                true);
            form.reset();
        } else if (response.status === 409) {
            showMessage(result || "Possible duplicate detected.", false);
        } else {
            throw new Error(result || `Request failed: ${response.status}`);
        }

        await loadApplications();
        await loadDuplicates();

    } catch (error) {
        showMessage("Request failed: " + error.message, false);
    }
});

document.getElementById("refreshApplications")
    .addEventListener("click", loadApplications);

document.getElementById("refreshDuplicates")
    .addEventListener("click", loadDuplicates);

loadApplications();
loadDuplicates();
