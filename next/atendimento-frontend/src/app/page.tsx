import { redirect } from "next/navigation";
import { Login } from "./components/Login"
import { Box, Card  } from "@chakra-ui/react"

export default function Home() {
    return (
    <Box>
      <Login />
    </Box>
  );
}