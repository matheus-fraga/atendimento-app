import { redirect } from "next/navigation";
import { Demo } from "../app/components/Demo"
import { Login } from "../app/components/Login"
import { Box, Card  } from "@chakra-ui/react"

export default function Home() {
    return (
    <Box>
      <Login />
    </Box>
  );
}