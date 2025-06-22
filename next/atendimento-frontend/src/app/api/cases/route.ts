export async function GET() {
  try {
    const response = await fetch('http://localhost:8080/admin/users', { //change to cases later
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2Rhc2Rhc2Rhc2Rhc2QiLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE3NTA0MjM4NjcsImV4cCI6MTc1MDQyNzQ2N30.ZFpGPRWiUC9Yh19OvsG38NvqFR3QP7wS2lKftjCGxv4',
      },
    });
    const data = await response.json();
    if(response.ok) {
      return new Response(JSON.stringify(data), {
        status: 200,
        headers: { 'Content-Type': 'application/json' },
      });
    }
    throw new Error(`HTTP ${data.status}: ${data.statusText}`);
  } catch (error) {
    console.error('Error proxying request:', error);
    return new Response(
      JSON.stringify({ error: 'Internal server error' }),
      { status: 500 }
    );
  }
}